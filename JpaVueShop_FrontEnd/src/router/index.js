import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/View/index'
import Layout from '../components/Layout/Layout'
import AdminLeftMenu from '../components/Admin/AdminLeftMenu'
import AdminRegisterItem from '../components/Admin/AdminRegisterItem'
import MyPageLeftMenu from '../components/MyPage/MyPageLeftMenu'
import UserInfo from '../components/MyPage/UserInfo'
import OrderList from '../components/MyPage/OrderList'
import CouponPoint from '../components/MyPage/CouponPoint'
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
        // ========== 관리자 페이지 ============
        {
          path: '/admin/adminLeftMenu',
          name: 'adminLeftMenu',
          component: AdminLeftMenu
        },
        {
          path: '/admin/adminRegisterItem',
          name: 'adminRegisterItem',
          component: AdminRegisterItem
        },
        // ========== 유저 페이지 =============
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
          path: '/myPage/orderList',
          name: 'orderList',
          component: OrderList
        },
        {
          path: '/myPage/couponPoint',
          name: 'couponPoint',
          component: CouponPoint
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
