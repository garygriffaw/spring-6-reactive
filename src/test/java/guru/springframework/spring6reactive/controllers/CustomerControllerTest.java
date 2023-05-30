package guru.springframework.spring6reactive.controllers;

import guru.springframework.spring6reactive.model.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListCustomers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    @Order(2)
    void testGetCustomerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testCreateCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(getTestCustomerDto()), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080" + CustomerController.CUSTOMER_PATH + "/5");
    }

    @Test
    void testCreateCustomerInvalidData() {
        CustomerDTO testCustomerDto = getTestCustomerDto();
        testCustomerDto.setName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(testCustomerDto), CustomerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void testUpdateCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getTestCustomerDto()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testUpdateCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(getTestCustomerDto()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testUpdateCustomerInvalidData() {
        CustomerDTO testCustomerDto = getTestCustomerDto();
        testCustomerDto.setName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomerDto), CustomerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(6)
    void testPatchCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(getTestCustomerDto()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testPatchCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(getTestCustomerDto()), CustomerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void testDeleteCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    private static CustomerDTO getTestCustomerDto() {
        return CustomerDTO.builder()
                .name("Test customer")
                .build();
    }
}