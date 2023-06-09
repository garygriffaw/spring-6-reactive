package guru.springframework.spring6reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6reactive.config.DatabaseConfig;
import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> System.out.println(beer.toString()));
    }

    @Test
    void createJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(getTestBeer()));
    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .beerName("New beer")
                .beerStyle("IPA")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(12)
                .upc("1234")
                .build();
    }
}