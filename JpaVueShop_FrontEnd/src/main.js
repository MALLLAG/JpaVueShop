// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import Vuetify from 'vuetify'
import store from './store'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import '@/assets/css/layout.css'
import '@/assets/css/main.css'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'vuetify/dist/vuetify.min.css'
import Util from './common/utils/Util'
// eslint-disable-next-line camelcase
import jwt_decode from 'jwt-decode'
import axios from 'axios'
import customAxios from './common/utils/axios'
import VueAdsPagination from 'vue-ads-pagination'
import '../node_modules/@fortawesome/fontawesome-free/css/all.css'
import '../node_modules/vue-ads-pagination/dist/vue-ads-pagination.css'

Vue.config.productionTip = false
Vue.use(Vuetify)
Vue.component('vue-ads-pagination', VueAdsPagination)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.prototype.$Util = Util
Vue.prototype.$axios = axios
Vue.prototype.$customAxios = customAxios
// eslint-disable-next-line camelcase
Vue.prototype.$jwt_decode = jwt_decode

/* eslint-disable no-new */
new Vue({
  vuetify: new Vuetify(),
  render: h => h(App),
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
