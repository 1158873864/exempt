import request from '@/utils/request'
export function merchantsReport() {
  return request({
    url: '/report/merchant',
    method: 'get'
  })
}

export function getPermerchantReport(uid) {
  // console.log(uid)
  return request({
      url: 'report/merchant/'+uid,
    method: 'get'
  })
}

export function fundingReport(){
  
  return request({
    url: '/report/funding',
    method: 'get',
    // params:{
    //   startDate,
    //   endDate
    // }
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
export function receiveCodeReport(){
  
  return request({
    url: '/report/receiptCode',
    method: 'get',

  })
}
export function supplierReport(){
  
  return request({
    url: '/report/supplier',
    method: 'get',

  })
}