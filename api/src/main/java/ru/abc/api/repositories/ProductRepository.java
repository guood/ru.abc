package ru.abc.api.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.abc.api.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product findByNameIgnoreCase(String name);
}
