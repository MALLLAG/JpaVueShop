# Spring boot + JPA + Vue.js를 이용한 쇼핑몰




<br/><br/>




# :pushpin: 사용기술
:paperclip: Backend
<div align=left>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/Elasticsearch-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white">
  <img src="https://img.shields.io/badge/Kibana-005571?style=for-the-badge&logo=Kibana&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
  <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
</div>

<br/>

:paperclip: Frontend
<div align=left>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
  <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
  <br/>
  <img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white">
  <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
</div>

<br/>

:paperclip: Devops
<div align=left>
  <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
</div>




<br/><br/><br/>




# :pushpin: 주요기능

:paperclip: 회원가입
<br/>
:paperclip: 로그인
<br/>
:paperclip: 장바구니
<br/>
:paperclip: 주문
<br/>
:paperclip: 쿠폰 등록/사용
<br/>
:paperclip: 포인트 사용
<br/>
:paperclip: 마이페이지
<br/>
:paperclip: 상품 검색
<br/>
:paperclip: 카테고리 등록 (관리자)
<br/>
:paperclip: 상품 등록 (관리자)
<br/>
:paperclip: 쿠폰 발행 (관리자)
<br/>




<br/><br/><br/>




# :pushpin: 핵심 로직/코드

:paperclip: spring batch + elasticsearch + kibana
<br/>
1. 엘라스틱서치에 인덱스 생성
```
PUT item
{
  "settings": {
    "analysis": {
      "analyzer": {
        "nori_mixed": {
          "tokenizer": "nori_t_mixed",
          "filter": "my_shingle"
        }
      },
      "tokenizer": {
        "nori_t_mixed": {
          "type": "nori_tokenizer",
          "decompound_mode": "mixed"
        }
      },
      "filter": {
        "my_shingle": {
          "type": "shingle",
          "token_separator": "",
          "max_shingle_size": 3
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "name": {
        "type": "text",
        "fields": {
          "nori_mixed": {
            "type": "text",
            "analyzer": "nori_mixed",
            "search_analyzer": "standard"
          }
        }
      }
    }
  }
}
```

<br/>

2. spring batch + quartz를 활용하여, RDBMS에 있는 검색에 필요한 데이터를 주기적으로 elasticsearch에 insert <br/>
(중복된 데이터가 있다면 insert하지않고 update한다)
``` java
    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
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
```

<br/>

3. RestHighLevelClient를 이용한 검색
``` java
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
```

<br/><br/>

:paperclip: JWT
<br/>
1. 로그인 시 accessToken과 refreshToken을 발급하고 accessToken은 header에, refreshToken은 httpOnly처리된 쿠키에 담아 프론트로 전송 <br/>
(accessToken의 만료시간은 10분, refreshToken의 만료시간은 1주일로 설정) <br/>
(refreshToken은 발급함과 동시에 해당 user 테이블에 저장)
``` java
    @Transactional
    public Long login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Map<String, Object> userData = new HashMap<>();

        User user = userRepo.findByUsername(loginReqDto.getUsername());
        if (user == null) {
            throw new CustomApiException("존재하지 않는 아이디입니다.");
        }

        if (bCryptPasswordEncoder.matches(loginReqDto.getPassword(), user.getPassword())) {
            userData.put("username", user.getUsername());
            userData.put("ROLE", user.getROLE());
        } else {
            throw new CustomApiException("잘못된 비밀번호입니다.");
        }

        Long userId = user.getId();

        String accessToken = jwtService.createAccessToken(userId + "", TEN_MINUTE, userData);
        String refreshToken = jwtService.createRefreshToken(A_WEEK);
        response.setHeader(ACCESS_TOKEN, accessToken);

        Cookie cookie = new Cookie(REFRESH_TOKEN, refreshToken);
        cookie.setMaxAge(A_WEEK);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        // 로그인시 refreshToken을 새로 발급받아 DB에 넣는다
        userRepoSup.setRefreshToken(userId, refreshToken);

        return userId;
    }
```

<br/>

2. 프론트에서 header에 담겨온 accessToken을 받아, localStorage에 저장 <br/>
(쿠키는 자동으로 저장된다)
``` javascript
  login () {
      let params = {}
      params['username'] = this.username
      params['password'] = this.password
      this.$axios.post('/api/user/login', params)
        .then(res => {
          if (res.data.code === 1) {
            localStorage.setItem('accessToken', res.headers.accesstoken)
            this.$router.push({path: '/'})
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    }
```

<br/>

3. 프론트에서 api요청을 할때마다, axios request interceptor가 localStorage에 있는 accessToken을 header에 실어서 보낸다
(api 요청을 할때마다 JWT를 검증)
``` javascript
customAxios.interceptors.request.use(async function (config) {
  const accessToken = localStorage.getItem('accessToken')
  config.headers.accessToken = accessToken
  return config
},
function (error) {
  return Promise.reject(error)
})
```

<br/>

4. 백엔드 interceptor에서 jwt를 검증
(accessToken이 만료되었다면, 쿠키에 담겨온 refreshToken과 user 테이블에 있는 refreshToken이 일치하는지 확인한 후 accessToken을 재발급해준다)
```java
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String accessToken = request.getHeader(ACCESS_TOKEN);
        String refreshToken = "";

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
            }
        }

        if (accessToken != null && jwtService.isUsable(accessToken)) {

            if (jwtService.isExpire(accessToken)) {
                throw new UnauthorizedException();
            }

            if (jwtService.isExpire(refreshToken)) {
                throw new RefreshTokenExpired();
            }

            return true;
        } else {
            throw new UnauthorizedException();
        }
    }
```
