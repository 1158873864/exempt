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
