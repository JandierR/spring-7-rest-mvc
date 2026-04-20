package guru.springframework.spring7restmvc.repositories;

import guru.springframework.spring7restmvc.bootstrap.BootstrapData;
import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByStyle() {
        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.IPA);
        assertThat(list.size()).isEqualTo(548);
    }

    @Test
    void testGetBeerListByName() {
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerDataTooLong() {


        assertThrows(ConstraintViolationException.class, () -> {

        Beer beer = beerRepository.save(Beer.builder()
                .beerName("New Name 922222222222222222222222222222222222222222222222222222222222222222222222222222222")
                .beerStyle(BeerStyle.IPA)
                .upc("23445")
                .price(new BigDecimal("23"))
                .build());

        beerRepository.flush();

        });


    }

    @Test
    void testSaveBeer() {
        Beer beer = beerRepository.save(Beer.builder()
                .beerName("New Name")
                        .upc("23445")
                        .beerStyle(BeerStyle.IPA)
                        .price(new BigDecimal("23"))
                .build());

        beerRepository.flush();

        assertThat(beer).isNotNull();
        assertThat(beer.getId()).isNotNull();
    }
}