<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-navbar-brand href="#">Home</b-navbar-brand>
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
      <b-navbar-brand @click="$router.push({path: '/login'})">로그인</b-navbar-brand>
    </b-navbar>
  </div>
</template>
<script>
export default {
  name: 'HeaderBar',
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

<style>
</style>
