package com.example.JpaVueShop_backend.service.api;

import com.example.JpaVueShop_backend.domain.category.Category;
import com.example.JpaVueShop_backend.domain.category.CategoryRepo;
import com.example.JpaVueShop_backend.domain.item.Item;
import com.example.JpaVueShop_backend.domain.item.ItemEsRepo;
import com.example.JpaVueShop_backend.domain.item.ItemRepoSup;
import com.example.JpaVueShop_backend.dto.api.item.ItemPageDto;
import com.example.JpaVueShop_backend.dto.api.item.ItemRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepoSup itemRepoSup;
    private final CategoryRepo categoryRepo;

    @Transactional
    public List<Map<String, Object>> getEsList() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));

        List<Map<String, Object>> itemList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("item");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = new BoolQueryBuilder();

        query.must(QueryBuilders.termQuery("price", 8000));
//        query.must(QueryBuilders.wildcardQuery("name", "*김치찌개*"));
        query.must(QueryBuilders.wildcardQuery("category.name", "*중식*"));
        sourceBuilder.query(query);
        sourceBuilder.from(0);
        sourceBuilder.size(10);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                Map<String, Object> sourceMap = searchHit.getSourceAsMap();
                itemList.add(sourceMap);
            }
            return itemList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 아이템 리스트 가져오기
     * @param itemPageDto
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getItemList(ItemPageDto itemPageDto) {
        String categoryName = itemPageDto.getCategory();
        Category category = categoryRepo.findByName(categoryName);

        double size = 10.0;
        Long totalCount = itemRepoSup.countItem(itemPageDto, category, size);
        List<Item> itemList = itemRepoSup.getItemList(itemPageDto, category, size);
        List<ItemRespDto> itemRespDtoList = new ArrayList<>();
        Map<String, Object> customPageData = new HashMap<>();

        for (Item item : itemList) {
            ItemRespDto itemRespDto = new ItemRespDto(item);
            itemRespDtoList.add(itemRespDto);
        }

        customPageData.put("content", itemRespDtoList);
        customPageData.put("totalElements", totalCount);
        customPageData.put("size", size);
        customPageData.put("pageNumber", itemPageDto.getCurrentPage());

        return customPageData;
    }
}
