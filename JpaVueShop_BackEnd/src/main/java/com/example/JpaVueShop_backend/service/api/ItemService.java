package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.domain.category.CategoryRepo;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemRepoSup;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
    private static final int PAGE_SIZE = 10;

    @Transactional(readOnly = true)
    public Map<String, Object> getItemList(ItemPageDto itemPageDto) throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                        new HttpHost(host, 9200, "http"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        httpClientBuilder.disableAuthCaching();
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }));

        List<Map<String, Object>> itemList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("item");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = new BoolQueryBuilder();

        Map<String, Object> customPageData = new HashMap<>();
        int currentPage = itemPageDto.getCurrentPage();
        String categoryName = itemPageDto.getCategory();
        String search = itemPageDto.getSearch();

        if (search != "") {
            query.must(QueryBuilders.matchQuery("name.nori_mixed", search));
            sourceBuilder.sort(SortBuilders.scoreSort());
        }
        if (categoryName != "") {
            query.must(QueryBuilders.termQuery("category.name", categoryName));
        }

        sourceBuilder.query(query);
        sourceBuilder.from(currentPage * PAGE_SIZE);
        sourceBuilder.size(PAGE_SIZE);
        sourceBuilder.sort(new FieldSortBuilder("itemId").order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                itemList.add(sourceMap);
            }

            customPageData.put("content", itemList);
            customPageData.put("totalElements", searchResponse.getHits().getTotalHits().value);
            customPageData.put("size", PAGE_SIZE);
            customPageData.put("pageNumber", currentPage);

            return customPageData;
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        client.close();

        return null;
    }

}
