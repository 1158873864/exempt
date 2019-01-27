import request from '@/utils/request'

export function cardAdd(accountOfBank,bank,bin,name,number,status,uid) {
  return request({
    url: '/usr/card/add',
    method: 'post',
    data: {
        accountOfBank,
        bank,
        bin,
        name,
        number,
        status,
        uid
    }
  })
}
export function cardsGet() {
  return request({
    url: '/usr/cards',
    method: 'get'
  })
}
export function cardsGetOne(uid) {
  return request({
    url: '/usr/cards/'+uid,
    method: 'get'
  })
}
export function qrCodeGet(id, ip, memo, merchantId, money, sign, time,type ) { 
  return request({
    url:'/qrCode/get',
    method: 'post',
    data:{
      id,
      ip,
      memo,
      merchantId,
      money,
      sign,
      time,
      type
    }
  })
}
export function redirect(url,orderId){
  return request({
    url: url,
    method: 'get',
    params:{
      orderId
    }
  })
}
