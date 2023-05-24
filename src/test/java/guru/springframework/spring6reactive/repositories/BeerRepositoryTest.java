package guru.springframework.spring6reactive.repositories;

import guru.springframework.spring6reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> System.out.println(beer.toString()));
    }

    Beer getTestBeer() {
        return Beer.builder()
                .beerName("New beer")
                .beerStyle("IPA")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(12)
                .upc("1234")
                .build();
    }
}