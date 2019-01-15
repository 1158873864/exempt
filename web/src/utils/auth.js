import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const Uid = '0'
const RolesKey = []

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getRoles() {
  return Cookies.get(RolesKey)
}

export function setRoles(Roles) {
  return Cookies.set(RolesKey, Roles)
}

export function removeRoles() {
  return Cookies.remove(RolesKey)
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
