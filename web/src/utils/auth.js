import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const Uid = '0'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
export function getUid() {
  return Cookies.get(Uid)
}

export function setUid(token) {
  return Cookies.set(Uid, token)
}

export function removeUid() {
  return Cookies.remove(Uid)
}
