<template>
  <div class="myPage">
    <MyPageLeftMenu></MyPageLeftMenu>
    <div class="myPage_content">
      <div class="userInfo">
        <h2>회원정보</h2>
        <table>
          <tbody>
          <tr>
            <td class="userInfo_col-1">아이디</td>
            <td class="userInfo_col-2"></td>
          </tr>
<!--          <tr>
            <td class="userInfo_col-1">이메일</td>
            <td class="userInfo_col-2">
              <b-form-input type="text" v-model="userData.email" placeholder="이메일을 입력해주세요" style="width: 70%;display: inline-block;" />
              <b-button @click="emailModify">수정</b-button>
            </td>
          </tr>
          <tr>
            <td class="userInfo_col-1">보유 포인트</td>
            <td class="userInfo_col-2">{{userData.point}}</td>
          </tr>
          <tr v-if="!$Util.isEmpty(userData.phone)">
            <td class="userInfo_col-1">휴대폰 번호</td>
            <td class="userInfo_col-2">{{userData.phone}}</td>
          </tr>
          <tr>
            <td class="userInfo_col-1">비밀번호 변경</td>
            <td class="userInfo_col-2"><b-button v-b-modal.modal-modify>비밀번호 변경</b-button></td>
          </tr>
          </tbody>
        </table>
        <b-modal id="modal-modify" title="비밀번호 변경" hide-footer>
          <p>
            <b-form-input type="password" v-model="currentPassword" placeholder="현재 비밀번호"></b-form-input>
          </p>
          <p>
            <b-form-input type="password" v-model="password" placeholder="비밀번호"></b-form-input>
          </p>
          <p>
            <b-form-input type="password" v-model="passwordCheck" placeholder="비밀번호 확인"></b-form-input>
          </p>
          <b-button @click="validation">변경</b-button>
        </b-modal>-->
      </div>
    </div>
  </div>
</template>
<script>
import MyPageLeftMenu from './MyPageLeftMenu'
export default {
  name: 'userInfo',
  components: {
    MyPageLeftMenu
  },
  data () {
    return {
      userData: {
        ROLE: ''
      }
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    fetchData () {
      this.$customAxios.get('/api/myPage/getUserData')
        .then(res => {
          if (res.data.code === 1) {
            this.userData = res.data.data
            this.userData.ROLE = res.data.data.role
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
