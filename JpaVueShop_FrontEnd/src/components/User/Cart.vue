<template>
  <div class="cart">
    <div class="cartList" v-if="step == 1">
      <h2>장바구니</h2>
      <table>
        <thead>
        <tr>
          <th>상품명</th>
          <th>가격</th>
          <th>삭제</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in cartList" :key="index">
          <td>{{ item.title }}</td>
          <td>{{ item.price }}</td>
          <td><b-button @click="deleteCartItem(item.cartItemId)">삭제</b-button></td>
        </tr>
        </tbody>
      </table>
      <div class="cartList_order">
        <button @click="nextStep">전체 상품 주문</button>
      </div>
    </div>
    <div class="cartOrder" v-else>
      <h2>주문/결제</h2>
      <hr/>
      <div class="userInfo">
        <h4>회원정보</h4>
        <table>
          <tbody>
          <tr>
            <td class="userInfo_col-1">주문자 아이디</td>
            <td class="userInfo_col-2">{{ userData.username }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <hr/>
      <div class="itemList">
        <h4>상품정보</h4>
        <table>
          <thead>
          <tr>
            <th>상품명</th>
            <th>가격</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in cartList" :key="index">
            <td>{{ item.title }}</td>
            <td>{{ item.price }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <hr/>
      <div class="paymentInfo">
        <h4>결제정보</h4>
        <table>
          <tbody>
          <tr>
            <td class="paymentInfo_col-1">총상품가격</td>
            <td class="paymentInfo_col-2">{{ $Util.formatNumber(form.totalPrice) }}</td>
          </tr>
          <tr>
            <td class="paymentInfo_col-1">최종결제가격</td>
            <td class="paymentInfo_col-2">{{ $Util.formatNumber(calcTotalPrice) }}</td>
          </tr>
          <tr>
            <td class="paymentInfo_col-1">할인쿠폰</td>
            <td class="paymentInfo_col-2">
              <select v-if="userData.userCouponDtoList.length != 0" v-model="form.usedCoupon">
                <option :value="null">쿠폰 사용안함</option>
                <option v-for="(item, index) in userData.userCouponDtoList" :key="index" :value="item" :disabled="item.amount > calcTotalPrice">
                  {{item.couponName}} ({{ $Util.formatNumber(item.amount) }}원)
                </option>
              </select>
              <select v-else>
                <option>
                  쿠폰이 없습니다
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td class="paymentInfo_col-1">포인트</td>
            <td class="paymentInfo_col-2">
              <input class="paymentInfo_point" type="text" v-model="form.usedPoint"
                     oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');">
              <span>보유 : {{ $Util.formatNumber(userData.point) }}원</span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="payment">
        <button @click="cartOrder">결제하기</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'cart',
  data () {
    return {
      step: 1,
      userData: {},
      cartList: [],
      form: {
        cartItemId: [],
        totalPrice: 0,
        usedPoint: 0,
        usedCoupon: {}
      }
    }
  },
  computed: {
    calcTotalPrice: function () {
      if (this.form.usedPoint > this.userData.point) {
        // eslint-disable-next-line vue/no-side-effects-in-computed-properties
        this.form.usedPoint = this.userData.point
      }
      let result = 0
      if (this.$Util.isEmpty(this.form.usedCoupon)) {
        if (this.form.usedPoint >= this.form.totalPrice) {
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.form.usedPoint = this.form.totalPrice
        }
        result = this.form.totalPrice - this.form.usedPoint
      } else {
        if (this.form.usedPoint >= this.form.totalPrice - this.form.usedCoupon.amount) {
          // eslint-disable-next-line vue/no-side-effects-in-computed-properties
          this.form.usedPoint = this.form.totalPrice - this.form.usedCoupon.amount
        }
        result = this.form.totalPrice - this.form.usedPoint - this.form.usedCoupon.amount
      }
      return result
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    cartOrder () {
      if (confirm('장바구니에 담긴 상품을 모두 주문하시겠습니까?')) {
        let _this = this
        let params = {}
        params['cartItemId'] = this.form.cartItemId
        params['userCouponId'] = this.form.usedCoupon.userCouponId
        params['usedPoint'] = this.form.usedPoint
        this.$customAxios.post('/api/cart/cartOrder', params)
          .then(res => {
            if (res.data.code === 1) {
              alert('주문이 완료되었습니다.')
              _this.$router.push({path: '/'})
            }
          })
          .catch(error => {
            alert(error.response.data.message)
          })
      }
    },
    nextStep () {
      this.step += 1
      this.getOrderUserData()
    },
    getOrderUserData () {
      this.$customAxios.get('/api/cart/getOrderUserData')
        .then(res => {
          if (res.data.code === 1) {
            this.userData = res.data.data
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    deleteCartItem (cartItemId) {
      let params = {}
      params['cartItemId'] = cartItemId
      this.$customAxios.post('/api/cart/deleteCartItem', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('장바구니에서 삭제되었습니다.')
            this.fetchData()
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    fetchData () {
      this.$customAxios.get('/api/cart/getCartList')
        .then(res => {
          if (res.data.code === 1) {
            this.cartList = res.data.data
            for (let i = 0; i < this.cartList.length; i++) {
              this.form.totalPrice = this.form.totalPrice + this.cartList[i].price
            }
            for (let i = 0; i < res.data.data.length; i++) {
              this.form.cartItemId[i] = res.data.data[i].cartItemId
            }
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
