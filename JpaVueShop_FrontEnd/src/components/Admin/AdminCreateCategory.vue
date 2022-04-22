<template>
  <div class="admin">
    <AdminLeftMenu></AdminLeftMenu>
    <div class="admin_content">
      <div class="createCategory">
        <h2>카테고리 등록</h2>
        <table>
          <tbody>
          <tr>
            <td class="createCategory_col-1">카테고리명</td>
            <td class="createCategory_col-2">
              <b-form-input type="text" v-model="name" />
            </td>
          </tr>
          <tr>
            <td class="createCategory_col-1">등록</td>
            <td class="createCategory_col-2">
              <b-button @click="beforeCreate">등록</b-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="categoryList">
        <h2>카테고리 목록</h2>
        <table>
          <thead>
          <tr>
            <th>카테고리번호</th>
            <th>카테고리명</th>
            <th>등록일자</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in categoryList" :key="index">
            <td>{{ item.categoryId }}</td>
            <td>{{ item.name }}</td>
            <td>{{ item.regDate.replace('T', ' ') }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import AdminLeftMenu from './AdminLeftMenu'
export default {
  name: 'adminCreateCategory',
  components: {
    AdminLeftMenu
  },
  data () {
    return {
      name: '',
      categoryList: []
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    beforeCreate () {
      if (this.$Util.isEmpty(this.name)) {
        alert('카테고리명를 입력해주세요.')
        return false
      }
      this.createCategory()
    },
    createCategory () {
      let params = {}
      params['name'] = this.name
      this.$customAxios.post('/admin/category/createCategory', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('카테고리가 생성되었습니다.')
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    fetchData () {
      this.$customAxios.get('/admin/category/getCategoryList')
        .then(res => {
          if (res.data.code === 1) {
            this.categoryList = res.data.data
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
