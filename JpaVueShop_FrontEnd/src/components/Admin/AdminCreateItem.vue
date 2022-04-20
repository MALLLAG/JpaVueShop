<template>
  <div class="admin">
    <AdminLeftMenu></AdminLeftMenu>
    <div class="admin_content">
      <div class="createItem">
        <h2>상품등록</h2>
        <table>
          <tbody>
          <tr>
            <td class="createItem_col-1">카테고리</td>
            <td class="createItem_col-2">
              <select v-model="category">
                <option v-for="(item, index) in categoryList" :key="index">{{ item.name }}</option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="createItem_col-1">상품명</td>
            <td class="createItem_col-2">
              <b-form-input type="text" v-model="name" />
            </td>
          </tr>
          <tr>
            <td class="createItem_col-1">가격</td>
            <td class="createItem_col-2">
              <b-form-input type="number" v-model="price" />
            </td>
          </tr>
          <tr>
            <td class="createItem_col-1">할인율</td>
            <td class="createItem_col-2">
              <b-form-input type="number" v-model="discountRate" />
            </td>
          </tr>
          <tr>
            <td class="createItem_col-1">등록</td>
            <td class="createItem_col-2">
              <b-button @click="beforeCreate">등록</b-button>
            </td>
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
  name: 'adminCreateItem',
  components: {
    AdminLeftMenu
  },
  data () {
    return {
      category: '',
      name: '',
      price: 0,
      discountRate: 0,
      categoryList: []
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    beforeCreate () {
      if (this.$Util.isEmpty(this.category)) {
        alert('카테고리를 선택해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.name)) {
        alert('상품명를 입력해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.price)) {
        alert('가격을 입력해주세요.')
        return false
      }
      if (this.price < 0) {
        alert('가격 0이상의 값만 입력가능합니다.')
        return false
      }
      if (this.$Util.isEmpty(this.discountRate)) {
        alert('할인율을 입력해주세요.')
        return false
      }
      if (this.discountRate < 0 || this.discountRate > 100) {
        alert('할인율은 0~100 사이의 숫자만 입력가능합니다.')
        return false
      }
      this.createItem()
    },
    createItem () {
      let params = {}
      params['category'] = this.category
      params['name'] = this.name
      params['price'] = this.price
      params['discountRate'] = this.discountRate
      this.$customAxios.post('/admin/item/createItem', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('상품이 등록되었습니다.')
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
