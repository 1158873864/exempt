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
export function permissionAllocate(post,permission) { 
    return request({
        url: '/company/permission/allocate',
        method:'post',
        data:{
            post,
            permission
        }
    })
 }
export function codesGet() {
  return request({
        url: '/company/codes',
        method: 'get',
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