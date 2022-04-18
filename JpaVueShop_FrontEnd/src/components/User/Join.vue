<template>
  <div class="join">
    <h2>회원가입</h2>
    <div class="join_input">
      <ul>
        <li>
          <p>
            <input type="text" v-model="username" name="username" placeholder="아이디">
          </p>
        </li>
        <li>
          <p>
            <input type="password" v-model="password" v-on:keyup.enter="join" name="password" placeholder="영문숫자 8자리 이상의 비밀번호">
          </p>
        </li>
        <button @click="beforeJoin">회원가입</button>
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
      role: '',
      username: '',
      password: ''
    }
  },
  methods: {
    beforeJoin () {
      if (this.$Util.isEmpty(this.username)) {
        alert('아이디를 입력해주세요.')
        return false
      }
      if (!this.$Util.isPasswordValid(this.password)) {
        alert('비밀번호를 영문숫자 8자리 이상으로 입력해주세요')
        return false
      }
      this.join()
    },
    join () {
      let params = {}
      params['role'] = 'USER'
      params['username'] = this.username
      params['password'] = this.password
      this.$axios.post('/api/user/join', params)
        .then(res => {
          if (res.data.code === 1) {
            alert(res.data.message)
            this.$router.push({path: '/login'})
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
