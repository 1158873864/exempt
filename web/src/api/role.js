import request from '@/utils/request'

export function addRole(code,operator,password,post,status,team,username) {
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
