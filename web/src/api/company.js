import request from '@/utils/request'

export function codeAdd(duration,info,number,priority,team,type) {
  return request({
      url: '/company/code/add',
        method: 'post',
        data: {
            duration,
            info,
            number,
            priority,
            team,
            type
        }
  })
}
export function codeInfo(id) {
    return request({
        url: '/company/code/info/'+id,
        method: 'get'
    })
}
export function codeDelete(id,verifyCode) {
    return request({
        url: '/company/code/delete/'+id,
        method: 'get',
        params:{
          verifyCode
        }
    })
}
export function permissionAllocate(post,permissions) { 
    return request({
        url: '/company/permission/allocate',
        method:'post',
        data:{
            post,
            permissions
        }
    })
 }
export function checkSinglePermission(post) {
  return request({
    url: '/company/permission',
    method: 'get',
    params: {
      post
    }
  })
}
export function checkAllPermission() {
  return request({
    url: '/company/permissions',
    method: 'get',
    params: {
      
    }
  })
}
export function codesGet() {
  return request({
    url: '/company/codes',
    method: 'get'
  })
}
export function waitApprovalMer() {
  return request({
    url: '/company/approval/merchants',
    method: 'get',
    params: {
    }
  })
}
export function ApprovalMer(mid, state) {
  return request({
    url: '/company/approval/merchant/'+mid,
    method: 'get',
    params: {
      
      state

    }
  })
}
export function waitApprovalSup() {
  return request({
    url: '/company/approval/suppliers',
    method: 'get',
    params: {
    }
  })
}
export function ApprovalSup(sid, state) {
  return request({
    url: '/company/approval/supplier/'+sid,
    method: 'get',
    params: {
      
      state

    }
  })
}
export function cardAdd(attribution,balance,bank,name,number,relation,status) {
  return request({
    url: '/company/card/add',
    method: 'post',
    data: {
      attribution,
      balance,
      bank,
      name,
      number,
      relation,
      status
    }
  })
}
export function cardsGet() {
  return request({
      url: '/company/cards',
        method: 'get',
  })
}
export function cardDelete(id,verifyCode) {
  console.log(id)
  return request({
      url: '/company/card/delete/'+id,
      method: 'get',
      params:{ verifyCode }
  })
}
/**
 * 
 * @param {岗位} post 
 */
export function addPost(post) {
  return request({
    url: '/company/post/add',
    method: 'get',
    params: {
      post,
    }
  })
}
export function deletePost(id) {
  return request({
    url: '/company/post/delete/'+id,
    method: 'get'
  })
}
export function postGet() {
  return request({
    url: '/company/post/list',
    method: 'get',
    params: {
    }
  })
}


export function teamAdd(area, operator, status, supervisor, teamName, verifyCode) {
  return request({
    url: '/company/team/add',
    method: 'post',
    data: {
      area,
      operator,
      status,
      supervisor,
      teamName,
      verifyCode
    }
  })
}
export function teamsGet() {
  return request({
    url: '/company/teams',
    method: 'get'
  })
}
export function teamDelete(id, verifyCode) {
  return request({
    url: '/company/team/delete/'+id,
    method: 'get',
    params: {
      id,
      verifyCode
    }
  })
}
export function teamVerifyCodeCheck(id,verifyCode) {
  return request({
    url: '/company/team/verify/'+id,
    method: 'get',
    params:{
      id,
      verifyCode
    }
  })
}
export function teamUpdate(area, operator, status, supervisor, teamName, verifyCode,id) {
  return request({
    url: '/company/team/update/'+id,
    method: 'post',
    data: {
      area,
      operator,
      status,
      supervisor,
      teamName,
      verifyCode
    }
  })
}
export function supplierUpdate(codeType, level, password, id) {
  return request({
    url: '/supplier/update'+id,
    method: 'post',
    data: {
      codeType,
      level,
      password
    }
  })
}
export function titleUpdate(title) {
  return request({
    url: '/company/sys/update',
    method: 'post',
    data: {
      title,
    }
  })
 }
export function titleList(){
  return request({
    url: '/company/sys/list',
    method: 'get',
  })
}