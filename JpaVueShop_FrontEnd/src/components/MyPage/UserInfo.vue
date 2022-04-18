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
            <td class="userInfo_col-2">{{userData.username}}</td>
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
        </b-modal>
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
      currentPassword: '',
      password: '',
      passwordCheck: '',
      userData: {
        ROLE: ''
      }
    }
  },
  created () {
    this.fetchData()
  },
  methods: {
    validation () {
      if (!this.$Util.isPasswordValid(this.currentPassword)) {
        alert('비밀번호를 입력해주세요.')
        return false
      }
      if (!this.$Util.isPasswordValid(this.password)) {
        alert('비밀번호를 입력해주세요.')
        return false
      }
      if (!this.$Util.isPasswordValid(this.passwordCheck)) {
        alert('비밀번호를 입력해주세요.')
        return false
      }
      if (this.password !== this.passwordCheck) {
        alert('비밀번호가 일치하지 않습니다')
        return false
      }

      let params = {}
      params['currentPassword'] = this.currentPassword
      this.$customAxios.post('/api/myPage/currentPasswordCheck', params)
        .then(res => {
          if (res.data.data === true) {
            this.passwordModify()
          } else {
            alert('현재 비밀번호가 일치하지 않습니다.')
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
    passwordModify () {
      let _this = this
      let params = {}
      params['password'] = this.password
      this.$customAxios.post('/api/myPage/passwordModify', params)
        .then(res => {
          if (res.data.code === 1) {
            alert('비밀번호 변경이 완료되었습니다.\n변경된 비밀번호로 다시 로그인해주세요')
            localStorage.removeItem('accessToken')
            _this.$router.push({path: '/'})
          }
        })
        .catch(error => {
          alert(error.response.data.message)
        })
    },
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
