<template>
  <div class="admin_leftMenu">
    <nav>
      <div class="admin-nav">
        <p>가입유저</p>
        <p @click="$router.push({path: '/admin/adminRegisterItem'}).catch(() => {})">상품등록</p>
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
