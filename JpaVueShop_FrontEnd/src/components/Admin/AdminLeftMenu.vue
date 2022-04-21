<template>
  <div class="admin_leftMenu">
    <nav>
      <div class="admin-nav">
        <p @click="$router.push({path: '/admin/adminUserList'}).catch(() => {})">유저 목록</p>
        <p @click="$router.push({path: '/admin/adminCreateCategory'}).catch(() => {})">카테고리 등록</p>
        <p @click="$router.push({path: '/admin/adminEventCoupon'}).catch(() => {})">쿠폰 등록</p>
        <p @click="$router.push({path: '/admin/adminCreateItem'}).catch(() => {})">상품 등록</p>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'adminLeftMenu',
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
.admin-nav {width: 10rem;}
</style>
