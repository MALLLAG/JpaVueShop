<template>
  <div>
    <button @click="test()">로그인</button>
  </div>
</template>
<script>
export default {
  name: 'login',
  components: {
  },
  data () {
    return {}
  },
  methods: {
    test () {
      this.$axios.get('/api/user/login')
        .then(res => {
          console.log(res)
        })
        .catch(error => {
          console.log(error)
        })
    }
  }
}
</script>
<style scoped="scoped">
</style>
