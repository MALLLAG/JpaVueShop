package com.example.JpaVueShop_backend.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemEsRepo extends ElasticsearchRepository<Item, Long> {
    List<Item> findByName(String name);
    List<Item> findByPrice(int price);

    Item existsByPrice(int price);
}
