<template>
  <div class="wrap" id="wrap1">
    <header-bar style="box-shadow: none"></header-bar>
    <router-view></router-view>
    <footer-bar></footer-bar>
  </div>
</template>
<script>
import HeaderBar from './HeaderBar'
import FooterBar from './FooterBar'
export default {
  components: {
    HeaderBar,
    FooterBar
  },
  data () {
    return {}
  }
}
</script>
<!--<style lang="scss">-->
<!--</style>-->
