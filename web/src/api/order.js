import request from '@/utils/request'
export function ordersGet() {
  return request({
    url: '/order/list',
    method: 'get'
  })
}