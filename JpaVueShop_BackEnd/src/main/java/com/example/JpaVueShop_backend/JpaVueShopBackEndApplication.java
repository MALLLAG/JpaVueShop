package com.example.JpaVueShop_backend;

//import com.example.JpaVueShop_backend.domain.item.ItemEsRepo;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/*@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = ItemEsRepo.class))*/
@SpringBootApplication
@EnableCaching
@EnableBatchProcessing
@EnableScheduling
public class JpaVueShopBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaVueShopBackEndApplication.class, args);
    }

}
