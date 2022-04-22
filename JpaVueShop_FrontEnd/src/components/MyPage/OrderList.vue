<template>
  <div class="myPage">
    <MyPageLeftMenu></MyPageLeftMenu>
    <div class="myPage_content">
      <div class="orderList">
        <h2>주문내역</h2>
        <table>
          <thead>
          <tr>
            <th>주문서</th>
            <th>가격</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in pageData.content" :key="index">
            <td>{{ item.orderTitle }}</td>
            <td>{{ item.paymentPrice }}</td>
          </tr>
          </tbody>
        </table>
        <vue-ads-pagination
          :total-items="pageData.totalElements"
          :max-visible-pages="pageData.size"
          :page="pageData.pageable.pageNumber"
          :loading="false"
          @page-change="paging"
          style="width: 50%;font-size: 15px;"/>
      </div>
    </div>
  </div>
</template>
<script>
import MyPageLeftMenu from './MyPageLeftMenu'
export default {
  name: 'orderList',
  components: {
    MyPageLeftMenu
  },
  data () {
    return {
      pageData: {
        content: [],
        empty: '',
        first: '',
        last: '',
        number: 0,
        pageable: {},
        size: 0,
        totalElements: 0,
        totalPages: 0
      }
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    paging (page) {
      this.pageData.pageable.pageNumber = page
      this.fetchData(this.pageData.pageable.pageNumber)
    },
    fetchData (currentPage) {
      let params = {}
      params['currentPage'] = currentPage
      this.$customAxios.post('/api/myPage/getOrderList', params)
        .then(res => {
          if (res.data.code === 1) {
            this.pageData = res.data.data
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    }
  }
}
</script>
<style scoped="scoped">
</style>
