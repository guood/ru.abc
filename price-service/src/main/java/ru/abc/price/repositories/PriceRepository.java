package ru.abc.price.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.abc.price.domain.Price;

import java.util.List;

public interface PriceRepository extends MongoRepository<Price, String> {

    List<Price> findByProductId(Integer productId);
}
