import request from '@/utils/request'
export function merchantsReport() {
  return request({
    url: '/report/merchants',
    method: 'get'
  })
}

export function getPermerchantReport(uid) {
  console.log(uid)
  return request({
      url: 'report/merchant/'+uid,
    method: 'get'
  })
}
