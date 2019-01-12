import request from '@/utils/request'

export function codeAdd(accountInfo,accountNumber,duration,id,priorityLevel,teamName,type) {
  return request({
      url: '/company/code/add',
        method: 'post',
        data: {
            accountInfo,
            accountNumber,
            duration,
            id,
            priorityLevel,
            teamName,
            type
        }
  })
}
export function codesGet() {
  return request({
      url: '/company/codes',
        method: 'get',
  })
}
