package com.example.JpaVueShop_backend.domain.item;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemEsRepo extends ElasticsearchRepository<Item, Long> {

}
