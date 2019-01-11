import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if false, the item will hidden in breadcrumb(default is true)
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/company',
    redirect: '/company/announcement',
    component: Layout,
    name: 'company',
    meta: { title: '公司管理', icon: 'example' },
    children: [
      {
        path: 'announcement',
        name: 'announcement',
        component: () => import('@/views/companyManage/announcement/index'),
        meta: { title: '公告管理' }
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views/companyManage/role/index'),
        meta: { title: '角色管理' }
      },
      {
        path: 'moneyReceiveCode',
        name: 'moneyReceiveCode',
        component: () => import('@/views/companyManage/moneyReceiveCode/index'),
        meta: { title: '收款码新增' }
      },
      {
        path: 'team',
        name: 'team',
        component: () => import('@/views/companyManage/team/index'),
        meta: { title: '团队管理' }
      },
      {
        path: 'privilege',
        name: 'privilege',
        component: () => import('@/views/companyManage/privilege/index'),
        meta: { title: '权限管理' }
      },
      {
        path: 'bankCard',
        name: 'bankCard',
        component: () => import('@/views/companyManage/bankCard/index'),
        meta: { title: '银行卡管理' }
      },
      {
        path: 'riskControl',
        name: 'riskControl',
        component: () => import('@/views/companyManage/riskControl/index'),
        meta: { title: '风控管理' }
      },
      {
        path: 'settings',
        name: 'settings',
        component: () => import('@/views/companyManage/settings/index'),
        meta: { title: '系统设置' }
      }
    ]
  },

  {
    path: '/user',
    redirect: '/user/agency',
    component: Layout,
    name: 'user',
    meta: {
      title: '用户中心',
      icon: 'team'
    },
    children: [
      {
        path: 'agency',
        component: () => import('@/views/userCenter/agency/index'),
        name: 'agency',
        meta: { title: '代理管理' }
      },
      {
        path: 'userManage',
        component: () => import('@/views/userCenter/userManage/index'),
        name: 'userManage',
        meta: { title: '用户管理' }
      },
      {
        path: 'bankCardSetting',
        component: () => import('@/views/userCenter/bankCardSetting/index'),
        name: 'bankCardSetting',
        meta: { title: '银行卡设置' }
      }
    ]
  },

  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/receiveCodeList',
    name: 'finance',
    meta: {
      title: '财务管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'receiveCodeList',
        component: () => import('@/views/financeManage/receiveCodeList/index'),
        name: 'receiveCodeList',
        meta: { title: '收款码列表' }
      },
      {
        path: 'bankCardList',
        component: () => import('@/views/financeManage/bankCardList/index'),
        name: 'bankCardList',
        meta: { title: '银行卡列表' }
      },
    ]
  },

  {
    path: '/report',
    redirect: '/report/receiveCodeReport',
    component: Layout,
    name: 'report',
    meta: {
      title: '报表统计',
      icon: 'report'
    },
    children: [
      {
        path: 'receiveCodeReport',
        component: () => import('@/views/report/receiveCodeReport/index'),
        name: 'receiveCodeReport',
        meta: { title: '收款码报表' }
      },
      {
        path: 'merchantsReport',
        component: () => import('@/views/report/merchantsReport/index'),
        name: 'merchantsReport',
        meta: { title: '商户报表' }
      },
      {
        path: 'localTeamReport',
        component: () => import('@/views/report/localTeamReport/index'),
        name: 'localTeamReport',
        meta: { title: '地方团队报表' }
      },
      {
        path: 'fundReport',
        component: () => import('@/views/report/fundReport/index'),
        name: 'fundReport',
        meta: { title: '资金报表' }
      },
      {
        path: 'agencyReport',
        component: () => import('@/views/report/agencyReport/index'),
        name: 'agencyReport',
        meta: { title: '代理报表' }
      }
    ]
  },

  {
    path: '/order',
    redirect: '/order/orderDetails',
    component: Layout,
    name: 'order',
    meta: {
      title: '订单管理',
      icon: 'order'
    },
    children: [
      {
        path: 'orderDetails',
        component: () => import('@/views/order/orderDetails/index'),
        name: 'orderDetails',
        meta: { title: '订单明细' }
      },
      {
        path: 'withdrawOrder',
        component: () => import('@/views/order/withdrawOrder/index'),
        name: 'withdrawOrder',
        meta: { title: '商户提现订单' }
      },
      {
        path: 'codeChangeOrder',
        component: () => import('@/views/order/codeChangeOrder/index'),
        name: 'codeChangeOrder',
        meta: { title: '内部码帐变订单' }
      },
      {
        path: 'cardChangeOrder',
        component: () => import('@/views/order/cardChangeOrder/index'),
        name: 'cardChangeOrder',
        meta: { title: '内部卡帐变订单' }
      },
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
