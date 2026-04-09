package guru.springframework.spring7restmvc.bootstrap;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        if (beerRepository.count() == 0 && customerRepository.count() == 0) {


            Beer beer1 = Beer.builder()
                    .beerName("Imperial")
                    .beerStyle(BeerStyle.IPA)
                    .upc("32324")
                    .price(new BigDecimal(12))
                    .quantityOnHand(5)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();


            beerRepository.save(beer1);


            Customer customer1 = Customer.builder()
                    .customerName("Jandier")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            customerRepository.save(customer1);

        }
    }
}
