package com.tutrit.bdd.repo;

import com.tutrit.bdd.model.Coin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CoinRepo extends CrudRepository<Coin, Long> {
}
