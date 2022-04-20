<template>
  <div class="admin">
    <AdminLeftMenu></AdminLeftMenu>
    <div class="admin_content">
      <div class="orderList">
        <h2>유저 목록</h2>
        <table>
          <thead>
          <tr>
            <th>아이디</th>
            <th>가입일자</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in pageData.content" :key="index">
            <td>{{ item.username }}</td>
            <td>{{ item.regDate.replace('T', ' ') }}</td>
          </tr>
          </tbody>
        </table>
        <vue-ads-pagination
          :total-items="pageData.totalElements"
          :max-visible-pages="pageData.size"
          :page="pageData.pageable.pageNumber"
          :loading="false"
          @page-change="paging"
          style="width: 45rem;font-size: 15px;"/>
      </div>
    </div>
  </div>
</template>
<script>
import AdminLeftMenu from './AdminLeftMenu'
export default {
  name: 'adminUserList',
  components: {
    AdminLeftMenu
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
      this.$customAxios.post('/admin/user/getUserList', params)
        .then(res => {
          if (res.data.code === 1) {
            console.log(res)
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
