import request from '@/utils/request'
export function merchantsReport() {
  return request({
    url: '/report/merchant',
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

export function agencyReport(){
  
  return request({
    url: '/report/agent',
    method: 'get',
    // params:{
    //   startDate,
    //   endDate
    // }
  })
}