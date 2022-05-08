<template>
  <div class="index">
    <div class="itemList">
      <table>
        <thead>
        <tr>
          <th>상품명</th>
          <th>가격</th>
          <th>카테고리</th>
          <th>장바구니</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in customPageData.content" :key="index">
          <td>{{ item.name }}</td>
          <td>{{ item.price }}</td>
          <td>{{ item.category.name }}</td>
          <td><b-button variant="outline-primary" @click="addCart(item.itemId)">담기</b-button></td>
        </tr>
        </tbody>
      </table>
      <div class="searchBar">
        <select v-model="category" @change="getItemList(0, category, search)">
          <option v-for="(item, index) in categoryList" :key="index">
            {{item.name}}
          </option>
        </select>
        <input v-model="search" placeholder="Search" />
        <button @click="getItemList(0, category, search)">검색</button>
      </div>
      <vue-ads-pagination
        :total-items="customPageData.totalElements"
        :max-visible-pages="customPageData.size"
        :page="customPageData.pageNumber"
        :loading="false"
        @page-change="paging"
        style="width: 50%;font-size: 15px;"/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      category: '한식',
      search: '',
      categoryList: [],
      customPageData: {
        content: [],
        size: 0,
        totalElements: 0,
        pageNumber: 0
      }
    }
  },
  created () {
    this.getCategoryList()
    this.getItemList(this.customPageData.pageNumber, this.category, this.search)
  },
  methods: {
    addCart (itemId) {
      let params = {}
      params['itemId'] = itemId
      this.$customAxios.post('/api/cart/addCart', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('상품을 장바구니에 담았습니다!')
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    paging (page) {
      this.customPageData.pageNumber = page
      this.getItemList(this.customPageData.pageNumber, this.category, this.search)
    },
    getCategoryList () {
      this.$customAxios.get('/api/category/getCategoryList')
        .then(res => {
          this.categoryList = res.data.data
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    getItemList (currentPage, category, search) {
      let params = {}
      params['currentPage'] = currentPage
      params['category'] = category
      params['search'] = search
      this.$customAxios.post('/api/item/getItemList', params)
        .then(res => {
          if (res.data.code === 1) {
            this.customPageData = res.data.data
            console.log(res.data.data)
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    }
  }
}
</script>
<style scoped>
</style>
