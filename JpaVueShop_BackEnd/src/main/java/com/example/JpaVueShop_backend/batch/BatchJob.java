package com.example.JpaVueShop_backend.batch;

import com.example.JpaVueShop_backend.domain.item.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BatchJob {

    private final int CHUNK_SIZE = 10;

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job elasticsearchJob() {
        return jobBuilderFactory.get("elasticsearchJob")
                .start(elasticsearchJobStep())
                .build();
    }

    @Bean
    public Step elasticsearchJobStep() {
        return stepBuilderFactory.get("elasticsearchStep")
                .<Item, Item>chunk(CHUNK_SIZE)
                .reader(reader())
                .processor(processor())
                .writer(new JpaItemWriter<Item>() {
                    @SneakyThrows
                    @Override
                    public void write(List<? extends Item> itemList) {
                        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                                new HttpHost("localhost", 9200, "http")));
                        BulkRequest bulkRequest = new BulkRequest();

                        for (Item item : itemList) {
                            IndexRequest indexRequest = new IndexRequest()
                                    .index("item")
                                    .id(String.valueOf(item.getId()))
                                    .source(XContentFactory.jsonBuilder()
                                            .startObject()
                                            .field("itemId", item.getId())
                                            .field("name", item.getName())
                                            .field("price", item.getPrice())
                                            .field("regDate", item.getRegDate())
                                                .startObject("category")
                                                .field("categoryId", item.getCategory().getId())
                                                .field("name", item.getCategory().getName())
                                                .endObject()
                                            .endObject());

                            UpdateRequest updateRequest = new UpdateRequest()
                                    .index("item")
                                    .id(String.valueOf(item.getId()))
                                    .doc(XContentFactory.jsonBuilder()
                                            .startObject()
                                            .field("itemId", item.getId())
                                            .field("name", item.getName())
                                            .field("price", item.getPrice())
                                            .field("regDate", item.getRegDate())
                                                .startObject("category")
                                                .field("categoryId", item.getCategory().getId())
                                                .field("name", item.getCategory().getName())
                                                .endObject()
                                            .endObject())
                                    .upsert(indexRequest);

                            bulkRequest.add(updateRequest);
                        }

                        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

                        if (bulkResponse.hasFailures()) {
                            log.error("Batch Elasticsearch Error: {} ", bulkResponse.buildFailureMessage());
                        } else {
                            log.info("Batch Elasticsearch Success: {} " + bulkResponse.toString());
                        }

                        client.close();
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Item> reader() {
        JpaPagingItemReader<Item> reader = new JpaPagingItemReader<>();
        reader.setQueryString("SELECT i FROM Item i");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(CHUNK_SIZE);

        return reader;
    }

    public ItemProcessor<Item, Item> processor() {
        return new ItemProcessor<Item, Item>() {
            @Override
            public Item process(Item item) throws Exception {
                return item;
            }
        };
    }

}