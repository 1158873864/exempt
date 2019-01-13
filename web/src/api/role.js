import request from '@/utils/request'

export function addAdmin(code, operator, password, post, status, team, username) {
  return request({
    url: '/admin/add',
    method: 'post',
    data: {
      code,
      operator,
      password,
      post,
      status,
      team,
      username
    }
  })
}
export function addAgent(brokerage, code, flow, password, status, username) {
  return request({
    url: '/agent/add',
    method: 'post',
    data: {
      brokerage,
      code,
      flow,
      password,
      status,
      username
    }
  })
}
export function addMerchant(alipay, balance, code, level, password, superior, username, wechat) {
  return request({
    url: '/merchant/add',
    method: 'post',
    data: {
      alipay,
      balance,
      code,
      level,
      password,
      superior,
      username,
      wechat
    }
  })
}
export function addSupplier(alipayloginID, password, username) {
  return request({
    url: '/supplier/add',
    method: 'post',
    data: {
      alipayloginID,
      password,
      username
    }
  })
}
export function deleteAdmin(aid) {
  return request({
    url: '/admin/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function deleteAgent(aid) {
  return request({
    url: '/agent/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function deleteMerchant(aid) {
  return request({
    url: '/merchant/delete',
    method: 'get',
    params: {
      aid
    }
  })
}
export function deleteSupplier(sid) {
  return request({
    url: '/supplier/delete',
    method: 'get',
    params: {sid }
  })
}