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
          <td>{{ item.category }}</td>
          <td><b-button variant="outline-primary" @click="addCart(item.itemId)">담기</b-button></td>
        </tr>
        </tbody>
      </table>
      <vue-ads-pagination
        :total-items="customPageData.totalElements"
        :max-visible-pages="customPageData.size"
        :page="customPageData.pageNumber"
        :loading="false"
        @page-change="paging"
        style="width: 45rem;font-size: 15px;"/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'index',
  data () {
    return {
      customPageData: {
        content: [],
        size: 0,
        totalElements: 0,
        pageNumber: 0
      }
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    addCart (itemId) {
      alert(itemId)
    },
    paging (page) {
      this.customPageData.pageNumber = page
      this.fetchData(this.customPageData.pageNumber)
    },
    fetchData (currentPage) {
      let params = {}
      params['currentPage'] = currentPage
      this.$customAxios.post('/api/item/getItemList', params)
        .then(res => {
          if (res.data.code === 1) {
            this.customPageData = res.data.data
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
