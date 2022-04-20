<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand @click="$router.push({path: '/'}).catch(() => {})">Home</b-navbar-brand>
      <b-collapse id="nav-collapse" is-nav>
        <b-nav-item-dropdown text="Menu" left style="color: #fff;">
          <b-dropdown-item href="#">한식</b-dropdown-item>
          <b-dropdown-item href="#">중식</b-dropdown-item>
          <b-dropdown-item href="#">일식</b-dropdown-item>
          <b-dropdown-item href="#">양식</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-navbar-nav>
          <b-nav-item href="#">Link</b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-4">
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
      <div v-if="$Util.isEmpty(accessToken)">
        <b-navbar-brand @click="$router.push({path: '/join'})">회원가입</b-navbar-brand>
        <b-navbar-brand @click="$router.push({path: '/login'})">로그인</b-navbar-brand>
      </div>
      <div v-else>
        <div class="dropdown">
          <button class="dropdown_btn">{{userData.username}} 님</button>
          <div class="dropdown-content" v-if="userData.ROLE == 'USER'">
            <a @click="logout()">로그아웃</a>
            <a @click="$router.push({path: '/myPage/userInfo'}).catch(() => {})">마이 페이지</a>
          </div>
          <div class="dropdown-content" v-else>
            <a @click="logout()">로그아웃</a>
            <a @click="$router.push({path: '/admin/adminCreateItem'}).catch(() => {})">관리자 페이지</a>
          </div>
        </div>
      </div>
    </b-navbar>
  </div>
</template>
<script>
export default {
  name: 'HeaderBar',
  data () {
    return {
      accessToken: localStorage.getItem('accessToken'),
      userData: {
        ROLE: '',
        username: ''
      }
    }
  },
  created () {
    if (!this.$Util.isEmpty(this.accessToken)) {
      var decoded = this.$jwt_decode(this.accessToken)
      this.userData.username = decoded.userData.username
      this.userData.ROLE = decoded.userData.ROLE
    }
  },
  methods: {
    logout () {
      let _this = this
      if (confirm('로그아웃 하시겠습니까?')) {
        localStorage.removeItem('accessToken')
        _this.$router.push({path: '/'})
        _this.$router.go(0)
      }
    }
  }
}
</script>

<style>
</style>
