import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken,getRoles } from '@/utils/auth'
import { getUid, setUid, setRoles} from '../../utils/auth';

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    uid: getUid()
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_UID: (state, uid) => {
      state.uid = uid
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      console.log('ppp')
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          console.log(response)
          const data = response.data
          setToken(data.token)
          setUid(data.uid)
          commit('SET_TOKEN', data.token)
          commit('SET_UID', data.uid)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {                                                               
      return new Promise((resolve, reject) => {
        console.log('get info')
        getInfo(state.uid).then(response => {
          const data = response.data
          var roles = ['404', '面板','用户信息','用户中心']
          if (data.permissions && data.permissions.length > 0) { // 验证返回的roles是否是一个非空数组
            roles = roles.concat(data.permissions)
            commit('SET_ROLES', roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        resolve()
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
