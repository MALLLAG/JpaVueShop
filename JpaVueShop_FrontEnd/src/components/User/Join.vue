<template>
  <div class="login">
    <h2>회원가입</h2>
    <div class="login_input">
      <ul>
        <li>
          <p>
            <input type="text" v-model="username" name="username" placeholder="아이디">
          </p>
        </li>
        <li>
          <p>
            <input type="password" v-model="password" v-on:keyup.enter="join" name="password" placeholder="비밀번호">
          </p>
        </li>
        <button @click="join">회원가입</button>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  name: 'join',
  components: {
  },
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    beforeJoin () {
    },
    join () {
      let params = {}
      params['username'] = this.username
      params['password'] = this.password
      this.$axios.post('/api/user/join', params)
        .then(res => {
          if (res.data.code === 1) {
            alert(res.data.message)
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
