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
export function addAgent(alipay,
  password,
  status,
  username,
  wechat) {
  return request({
    url: '/agent/add',
    method: 'post',
    data: {
      alipay,
      password,
      status,
      username,
      wechat
    }
  })
}
export function addMerchant(
  alipay,
  applyId,
  level,
  password,
  username,
  wechat) {
  return request({
    url: '/merchant/add',
    method: 'post',
    data: {
      alipay,
      applyId,
      level,
      password,
      username,
      wechat,
    }
  })
}
export function addSupplier(id, level, password, username) {
  return request({
    url: '/supplier/add',
    method: 'post',
    data: {
      id,
      level,
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
export function updateMerchant(mid, alipay,
  level,
  name,
  password,
  status,
  wechat) {
  return request({
    url: '/merchant/update/'+mid,
    method: 'put',
    data: {
      alipay,
      level,
      name,
      password,
      status,
      wechat
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
export function adminsGet() {
  return request({
    url: '/admins',
    method: 'get'
  })
}
export function agentsGet() {
  return request({
    url: '/agents',
    method: 'get'
  })
}
export function merchantsGet() {
  return request({
    url: '/merchants',
    method: 'get'
  })
}
export function merchantsMy(id) {
  return request({
    url: '/myMerchants/'+id,
    method: 'get'
  })
}
export function suppliersGet() {
  return request({
    url: '/suppliers',
    method: 'get'
  })
}
export function supplierUpdate(codeType, level, password, id) {
  return request({
    url: '/supplier/update/'+id,
    method: 'put',
    data: {
      codeType,
      level,
      password
    }
  })
}