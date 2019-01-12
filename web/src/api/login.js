import request from '@/utils/request'
export function login(username, password) {
  return request({
    url: '/account/login',
    method: 'post',
    params: { username, password },
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function deleteAccount(id) {
  return request({
    url: '/account/delete',
    method: 'get',
    params: { id }
  })
}
