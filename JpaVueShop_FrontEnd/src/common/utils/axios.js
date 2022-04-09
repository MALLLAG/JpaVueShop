import axios from 'axios'
import router from '../../router'

const customAxios = axios.create({

})

customAxios.defaults.timeout = 2500
customAxios.defaults.withCredentials = true

// request 인터셉터
customAxios.interceptors.request.use(async function (config) {
  const accessToken = localStorage.getItem('accessToken')
  config.headers.accessToken = accessToken
  return config
},
function (error) {
  return Promise.reject(error)
})

// response 인터셉터
customAxios.interceptors.response.use(
  (res) => {
    return res
  },
  async function (error) {
    const originalRequest = error.config
    if (error.response.data.code === 401) {
      // accessToken 만료 시 refresh 토큰을 이용해서 accessToken을 새로 발급받는다
      return axios.get('/api/user/createToken')
        .then(res => {
          localStorage.setItem('accessToken', res.headers.accesstoken)
          return customAxios.request(originalRequest)
        })
        // refreshToken 검증에 실패했을때
        .catch(error => {
          alert(error.response.data.message)
          localStorage.removeItem('accessToken')
          router.push({path: '/user/login'})
        })
    } else if (error.response.data.code === 403) {
      // refreshToken 만료 시 강제로 로그아웃
      localStorage.removeItem('accessToken')
      alert('로그인 시간이 만료되었습니다.\n다시 로그인해주세요.')
      router.push({path: '/user/login'})
    } else {
      return Promise.reject(error)
    }
  }
)

export default customAxios
