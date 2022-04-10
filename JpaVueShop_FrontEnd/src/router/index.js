import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/View/index'
import Layout from '../components/Layout/Layout'
import MyPageLeftMenu from '../components/MyPage/MyPageLeftMenu'
import UserInfo from '../components/MyPage/UserInfo'
import Join from '../components/User/Join'
import Login from '../components/User/Login'
// import Util from '../common/utils/Util'
// import jwtDecode from 'jwt-decode'
Vue.use(Router)

// 관리자 권한 체크
// const requireAdminAuth = () => (to, from, next) => {
//   let accessToken = localStorage.getItem('accessToken')
//   if (!Util.isEmpty(accessToken)) {
//     var decoded = jwtDecode(accessToken)
//     if (decoded.userData.ROLE === 'ADMIN') {
//       return next()
//     }
//     alert('권한이 없습니다.')
//     next('/')
//   }
// }

const router = new Router({
  linkActiveClass: 'on',
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: '/',
      children: [
        {
          path: '/',
          name: 'index',
          component: Index
        },
        {
          path: '/myPage/myPageLeftMenu',
          name: 'myPageLeftMenu',
          component: MyPageLeftMenu
        },
        {
          path: '/myPage/userInfo',
          name: 'userInfo',
          component: UserInfo
        },
        {
          path: '/join',
          name: 'join',
          component: Join
        },
        {
          path: '/login',
          name: 'login',
          component: Login
        }
      ]
    }
  ]
})

export default router
