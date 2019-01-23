import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/utils/auth' // 验权

const whiteList = ['/login'] // 不重定向白名单
// permission judge function
function hasPermission(roles, permissionRoles) {
  // console.log('ppp',permissionRoles, roles.some(role => permissionRoles.indexOf(role) >= 0))
  if (roles.indexOf('admin') >= 0) return true // admin permission passed directly
  if (!permissionRoles) return true
  return roles.some(role => permissionRoles.indexOf(role) >= 0)
}
router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      // console.log(to, 'retyrty', store.getters.roles)
      if (store.getters.roles.length === 0) {
        // console.log('werwerwer')
        // console.log(store.getters.roles)
        store.dispatch('GetInfo').then(res => { // 拉取用户信息
          // console.log('werwerwersxcv')
          const roles = store.getters.roles;
          // console.log(store.getters.roles)
          store.dispatch('GenerateRoutes', { roles }).then(() => { // 生成可访问的路由表
            router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
            router.options.routes = store.getters.addRouters // 动态添加可访问路由表
            // console.log(router)
            // console.log('store.getters.addRouters',store.getters.addRouters)
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
          })
          next()
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || 'Verification failed, please login again')
            next({ path: '/' })
          })
        })
        // console.log('ppppppp',store.getters.roles, to.meta.role)

      } else {
        // console.log(to)
        if (to.meta.role){
          if (hasPermission(store.getters.roles, to.meta.role)) {
            next()
          } else {
            next({ path: '/401', replace: true, query: { noGoBack: true } })
          }
        }else{
          // console.log('没有权限对应的目录')
          next();
        }
        // var roles = store.getters.roles;
        // store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
        //   router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
        //   // next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        // })
        // next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
