<template>
  <div class="login">
    <h2>로그인</h2>
    <div class="login_input">
      <ul>
        <li>
          <p>
            <input type="text" v-model="username" name="username" placeholder="아이디">
          </p>
        </li>
        <li>
          <p>
            <input type="password" v-model="password" v-on:keyup.enter="login" name="password" placeholder="영문숫자 8자리 이상의 비밀번호">
          </p>
        </li>
        <button @click="login()">로그인</button>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  name: 'login',
  components: {
  },
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login () {
      let params = {}
      params['username'] = this.username
      params['password'] = this.password
      this.$axios.post('/api/user/login', params)
        .then(res => {
          if (res.data.code === 1) {
            console.log(res)
            localStorage.setItem('accessToken', res.headers.accesstoken)
            this.$router.push({path: '/'})
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
.login {margin: 8rem;}
.login h2 {text-align: center;}
.login .login_input {border: 1px solid rgba(12,18,28,.12);border-radius: 2rem;margin: 2rem auto;padding: 2rem;text-align: center;width: 42rem;}
.login .login_input ul {list-style: none;}
.login .login_input ul li {text-align: left;margin: 2rem;}
.login .login_input ul li p input {border: 1px solid rgba(12,18,28,.12);border-radius: 0.5rem;width: 33rem;height: 2.2rem;padding: 1rem;}
</style>
