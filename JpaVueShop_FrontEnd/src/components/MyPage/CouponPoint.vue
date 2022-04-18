<template>
  <div class="myPage">
    <MyPageLeftMenu></MyPageLeftMenu>
    <div class="myPage_content">
      <div class="couponPoint">
        <h2>쿠폰/포인트</h2>
        <table>
          <tbody>
          <tr>
            <td class="couponPoint_col-1">보유 쿠폰</td>
            <td class="couponPoint_col-2">
              <select v-if="couponList.length != 0">
                <option v-for="(item, index) in couponList" :key="index" :value="item.userCouponId">
                  {{item.couponName}} ({{item.amount}}원)
                </option>
              </select>
              <select v-else>
                <option>
                  쿠폰이 없습니다
                </option>
              </select>
            </td>
          </tr>
<!--          <tr>
            <td class="couponPoint_col-1">보유 포인트</td>
            <td class="couponPoint_col-2">{{userData.point}}</td>
          </tr>-->
          <tr>
            <td class="couponPoint_col-1">쿠폰등록</td>
            <td class="couponPoint_col-2"><b-button v-b-modal.modal-publish>쿠폰 등록</b-button></td>
          </tr>
          </tbody>
        </table>
      </div>
      <b-modal id="modal-publish" title="쿠폰 등록" hide-footer>
        <p>
          <b-form-input type="text" v-model="couponNumber" placeholder="쿠폰 번호를 입력해주세요"></b-form-input>
        </p>
        <button class="btn_pwd" @click="registerCoupon">등록</button>
      </b-modal>
    </div>
  </div>
</template>
<script>
import MyPageLeftMenu from './MyPageLeftMenu'
export default {
  name: 'couponPoint',
  components: {
    MyPageLeftMenu
  },
  data () {
    return {
      couponList: [],
      couponNumber: ''
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    registerCoupon () {
      let params = {}
      params['couponNumber'] = this.couponNumber
      this.$customAxios.post('/api/myPage/registerCoupon', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('쿠폰이 지급되었습니다!')
            this.fetchData()
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    fetchData () {
      this.$customAxios.get('/api/myPage/getCouponPoint')
        .then(res => {
          if (res.data.code === 1) {
            this.couponList = res.data.data
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
