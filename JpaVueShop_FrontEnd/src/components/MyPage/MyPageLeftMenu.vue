<template>
  <div class="myPage_leftMenu">
    <nav>
      <div class="myPage-nav">
        <p @click="$router.push({path: '/myPage/userInfo'}).catch(() => {})">회원정보</p>
      </div>
      <div class="myPage-nav">
        <p>주문내역</p>
        <p>쿠폰/포인트</p>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'myPageLeftMenu',
  data () {
    return {
    }
  },
  created () {
  },
  methods: {
  }
}
</script>
<style scoped="scoped">
.myPage-nav {width: 10rem;}
</style>
