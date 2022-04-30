package com.example.JpaVueShop_backend.batch;

import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemRepo;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class test {

    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/ssss")
    public void test() throws IOException {
        List<Item> itemList = itemRepo.findAll();

        //엘라스틱서치 접속객체 생성
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("localhost", 9200, "http")));
        BulkRequest bulkRequest = new BulkRequest();

        //데이터를 순차적으로 처리하기 위한 반복문
        for (Item item : itemList) {

            //인덱스 생성용 객체 생성 및 데이터삽입
            IndexRequest indexRequest = new IndexRequest()
                    .index("item")
                    .id(String.valueOf(item.getId()))
                    .source(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("id", item.getId())
                            .field("name", item.getName())
                            .field("price", item.getPrice())
                            .field("regDate", item.getRegDate())
                            .startObject("category")
                            .field("id", item.getCategory().getId())
                            .field("name", item.getCategory().getName())
                            .endObject()
                            .endObject());

            // 중복된 document가 있을 때, Update 하기 위한 객체 생성
            UpdateRequest updateRequest = new UpdateRequest()
                    .index("item")
                    .id(String.valueOf(item.getId()))
                    .doc(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("id", item.getId())
                            .field("name", item.getName())
                            .field("price", item.getPrice())
                            .field("regDate", item.getRegDate())
                            .startObject("category")
                            .field("id", item.getCategory().getId())
                            .field("name", item.getCategory().getName())
                            .endObject()
                            .endObject())
                    .upsert(indexRequest); // Upsert 구현을 위해 UpdateRequest를 함께 사용

            //bulk객체를 생성하여 엘라스틱서치에 전송할 데이터 세트를 생성함
            bulkRequest.add(updateRequest);
        }

        // 엘라스틱서치로 데이터 전송 및 처리결과 수신
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        if (bulkResponse.hasFailures()) {
            System.out.println("### NRF0101001 Batch Elasticsearch Error! - " + bulkResponse.buildFailureMessage());
        } else {
            System.out.println("### NRF0101001 Batch Elasticsearch Success! - " + bulkResponse.toString());
        }

        client.close();
    }
}
