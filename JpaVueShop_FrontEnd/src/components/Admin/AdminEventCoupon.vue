<template>
  <div class="admin">
    <AdminLeftMenu></AdminLeftMenu>
    <div class="admin_content">
      <div class="admin_eventCoupon">
        <ul>
          <li>
            <dl>
              <dt>쿠폰명</dt>
              <dd><b-form-input type="text" v-model="form.couponName" /></dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>쿠폰번호</dt>
              <dd><b-form-input type="text" v-model="form.couponNumber" /></dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>쿠폰금액</dt>
              <dd><b-form-input type="text" v-model="form.amount" /></dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt>기간</dt>
              <dd><b-form-datepicker id="startDate" v-model="form.startDate" class="mb-2" style="width: 18rem;" /></dd>
              <dd><b-form-datepicker id="endDate" v-model="form.endDate" class="mb-2" style="width: 18rem;" /></dd>
            </dl>
          </li>
        </ul>
        <button @click="validation">쿠폰등록</button>
        <hr>
        <table>
          <thead>
          <tr>
            <th>쿠폰명</th>
            <th>쿠폰번호</th>
            <th>쿠폰금액</th>
            <th>기간</th>
            <th>등록일자</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in pageData.content" :key="index">
            <td>{{item.couponName}}</td>
            <td>{{item.couponNumber}}</td>
            <td>{{item.amount}}</td>
            <td>{{item.startDate.slice(0, 10)}} ~ {{item.endDate.slice(0, 10)}}</td>
            <td>{{item.regDate.replace('T', ' ')}}</td>
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
import AdminLeftMenu from './AdminLeftMenu'
export default {
  name: 'adminEventCoupon',
  components: {
    AdminLeftMenu
  },
  data () {
    return {
      form: {
        couponName: '',
        couponNumber: '',
        amount: '',
        startDate: '',
        endDate: ''
      },
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
    validation () {
      if (this.$Util.isEmpty(this.form.couponName)) {
        alert('쿠폰명을 입력해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.form.couponNumber)) {
        alert('쿠폰번호를 입력해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.form.amount)) {
        alert('쿠폰금액를 입력해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.form.startDate)) {
        alert('쿠폰 시작일자를 입력해주세요.')
        return false
      }
      if (this.$Util.isEmpty(this.form.endDate)) {
        alert('쿠폰 종료일자를 입력해주세요.')
        return false
      }
      this.publishCoupon()
    },
    paging (page) {
      this.pageData.pageable.pageNumber = page
      this.fetchData(this.pageData.pageable.pageNumber)
    },
    publishCoupon () {
      this.$customAxios.post('/admin/coupon/publishCoupon', this.form)
        .then(res => {
          if (res.data.code === 1) {
            alert('쿠폰이 발행되었습니다.')
            this.form.couponName = ''
            this.form.couponNumber = ''
            this.form.amount = ''
            this.fetchData()
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    fetchData (currentPage) {
      let params = {}
      params['currentPage'] = currentPage
      this.$customAxios.post('/admin/coupon/getCouponList', params)
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
<style scoped>

</style>
