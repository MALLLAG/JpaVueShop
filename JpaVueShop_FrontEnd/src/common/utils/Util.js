'use strict'
// import '@babel/polyfill'
import 'babel-polyfill'
class Util {
  isEmpty (value) {
    return (value === '' || value === null || value === undefined || (value != null && typeof value === 'object' && !Object.keys(value).length))
  }
  /**
   * 휴대폰 번호 정규식
   *  - 01012341234 형식 체크
   * @class Util
   */
  isPhoneValid (value) {
    if (this.isEmpty(value)) return false
    let regExp = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/
    return (regExp.test(value))
  }
  /**
   * 이메일 정규식
   *  - test123@test.com
   * @class Util
   */
  isEmailValid (value) {
    if (this.isEmpty(value)) return false
    let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i
    return (regExp.test(value))
  }

  /**
   * 비밀번호 정규식
   *  - 영문숫자 8자리 이상 형식 체크
   * @class Util
   */
  isPasswordValid (value) {
    if (this.isEmpty(value)) return false
    let reg_num = /[0-9]/g
    let reg_eng = /[a-zA-Z]/ig
    return reg_num.test(value) && reg_eng.test(value) && value.length >= 8
    // let regExp = /^[A-Za-z0-9+]{8,}$/
    // let regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/
    // return (regExp.test(value))
  }

  /**
   * 한글영문 10자리 정규식
   *  - 한글 또는 영문만 입력가능
   * @class Util
   */
  isWordValid (value) {
    if (this.isEmpty(value)) return false
    let regExp = /^[가-힣a-zA-Z]{1,10}$/
    // let regExp = /^[가-힣]{1,10}$/
    return (regExp.test(value))
  }

  /**
   * 한글영문 이름 6자리 정규식
   *  - 한글 또는 영문만 입력가능
   * @class Util
   */
  isNameValid (value) {
    if (this.isEmpty(value)) return false
    // let regExp = /^[가-힣a-zA-Z]{1,10}$/
    let regExp = /^[가-힣]{2,10}$/
    return (regExp.test(value))
  }

  /**
   * 숫자만 입력가능
   * @param value
   * @returns {boolean|*}
   */
  isNumberValid (value) {
    if (this.isEmpty(value)) return false
    let regExp = /^[0-9]*$/
    return (regExp.test(value))
  }

  /**
   * 아이디 정규식
   * @param value
   * @returns {boolean}
   */
  isIdValid (value) {
    if (this.isEmpty(value)) return false
    return value.length >= 6
    // let regExp = /^[A-Za-z0-9]{5,15}$/
    // return (regExp.test(value))
  }

  formatNumber (value) {
    if ((this.isEmpty(value))) return 0
    return parseInt(value.toString().replace(/,/g, '')).toString().replace(/(\d)(?=(\d{3})+(?:\.\d+)?$)/g, '$1,')
  }
}
export default new Util()
