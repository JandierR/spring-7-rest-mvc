package guru.springframework.spring7restmvc.services;

import guru.springframework.spring7restmvc.model.Beer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface BeerService {
    List<Beer> beerList();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);
}
