import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'
import routerInclude from '../components/routerInclude'

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
    name: 'Dashboard',
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
    name: 'company1',
    role:['公司管理'],
    meta: { title: '公司管理', icon: 'example' },
    children: [
      {
        path: 'announcement',
        name: 'announcement',
        component: () => import('@/views/companyManage/announcement/index'),
        role:['公告管理'],
        meta: { title: '公告管理' }
      },
      {
        path: 'role',
        name: 'role',
        component: () => import('@/views/companyManage/role/index'),
        role:['角色管理'],
        meta: { title: '角色管理' }
      },
      {
        path: 'moneyReceiveCode',
        name: 'moneyReceiveCode',
        component: () => import('@/views/companyManage/moneyReceiveCode/index'),
        role:['收款码新增'],
        meta: { title: '收款码新增' }
      },
      {
        path: 'team',
        name: 'team',
        redirect: '/team',
        component: () => import('@/views/companyManage/team/index'),
        role:['团队管理'],
        meta: { title: '团队管理', breadcrumb: false },
        children:[
          {
            path: 'teams',
            name: 'teams',
            component: () => import('@/views/companyManage/team/teams/index'),
            role:['所有团队'],
            meta: { title: '所有团队' },
          },
          {
            path: 'teamAdd',
            name: 'teamAdd',
            component: () => import('@/views/companyManage/team/addTeam/index'),
            role:['添加团队'],
            meta: { title: '添加团队', breadcrumb: false },
            breadcrumb: false
          }
        ]
      },
      {
        path: 'adminManage',
        component: () => import('@/views/userCenter/adminManage/index'),
        name: 'adminManage',
        role:['管理员管理'],
        meta: { title: '管理员管理' },
        children: [
          {
            path: 'addAdmin',
            name: 'addAdmin',
            component: () => import('@/views/userCenter/adminManage/addAdmin/index'),
            role:['添加管理员'],
            meta: { title: '添加管理员' }
          },
          {
            path: 'admins',
            name: 'admins',
            component: () => import('@/views/userCenter/adminManage/admins/index'),
            role:['所有管理员'],
            meta: { title: '所有管理员' }
          }
        ]
      },
      {
        path: 'post',
        name: 'post',
        component: () => import('@/views/companyManage/post/index'),
        role:['岗位管理'],
        meta: { title: '岗位管理' },
        props: true,
        children: [
          {
            path: 'postAdd',
            name: 'permission',
            component: () => import('@/views/companyManage/post/addPost/index'),
            role:['岗位添加'],
            meta: { title: '岗位添加' }
          },
          {
            path: 'allpost',
            name: 'allpost',
            component: () => import('@/views/companyManage/post/allPost/index'),
            role:['所有岗位'],
            meta: { title: '所有岗位' }
          }
        ]
      },
      {
        path: 'privilege',
        name: 'privilege',
        component: () => import('@/views/companyManage/privilege/index'),
        role:['权限管理'],
        meta: { title: '权限管理' },
        children: [
          {
            path: 'permission',
            name: 'permission',
            component: () => import('@/views/companyManage/permission/allocate'),
            role:['分配权限'],
            meta: { title: '分配权限' }
          },
          {
            path: 'singlePermission',
            name: 'singlePermission',
            component: () => import('@/views/companyManage/permission/singlePermission/index'),
            role:['某个职位权限查询'],
            meta: { title: '某个职位权限查询' }
          },
          {
            path: 'allPermission',
            name: 'allPermission',
            component: () => import('@/views/companyManage/permission/allPermission/index'),
            role:['所有权限查询'],
            meta: { title: '所有权限查询' }
          }
        ]
      },
      {
        path: 'bankCard',
        name: 'bankCard',
        component: () => import('@/views/companyManage/bankCard/index'),
        role:['银行卡管理'],
        meta: { title: '银行卡管理' },
        children: [
          {
            path: 'cards',
            name: 'cards',
            component: () => import('@/views/companyManage/bankCard/cards/index'),
            role:['所有银行卡'],
            meta: { title: '所有银行卡' },
          },
          {
            path: 'cardAdd',
            name: 'cardAdd',
            component: () => import('@/views/companyManage/bankCard/cardAdd/index'),
            role:['添加银行卡'],
            meta: { title: '添加银行卡', breadcrumb: false },
            breadcrumb: false
          }
        ]
      },
      {
        path: 'riskControl',
        name: 'riskControl',
        component: () => import('@/views/companyManage/riskControl/index'),
        role:['风控管理'],
        meta: { title: '风控管理' }
      },
      {
        path: 'settings',
        name: 'settings',
        component: () => import('@/views/companyManage/settings/index'),
        role:['系统设置'],
        meta: { title: '系统设置' }
      }
    ]
  },

  {
    path: '/user',
    redirect: '/user/agency',
    component: Layout,
    name: 'user',
    role:['用户中心'],
    meta: {
      title: '用户中心',
      icon: 'team'
    },
    children: [
      {
        path: 'agency',
        component: () => import('@/views/userCenter/agency/index'),
        name: 'agency',
        role:['代理管理'],
        meta: { title: '代理管理' },
        children: [
          {
            path: 'addAgency',
            name: 'addAgency',
            component: () => import('@/views/userCenter/agency/addAgency/index'),
            role:['添加代理'],
            meta: { title: '添加代理' }
          },
          {
            path: 'agencys',
            name: 'agencys',
            component: () => import('@/views/userCenter/agency/agencys/index'),
            role:['所有代理'],
            meta: { title: '所有代理' }
          }    
        ]
      },
      {
        path: 'supplierManage',
        component: () => import('@/views/userCenter/supplierManage/index'),
        name: 'supplierManage',
        role:['供码用户管理'],
        meta: { title: '供码用户管理' },
        children: [
          {
            path: 'addsupplier',
            name: 'addsupplier',  
            component: () => import('@/views/userCenter/supplierManage/addsupplier/index'),
            role:['添加供码用户'],
            meta: { title: '添加供码用户' }
          },
          {
            path: 'waitApprovalSup',
            name: 'waitApprovalSup',  
            component: () => import('@/views/userCenter/supplierManage/waitApprovalSup/index'),
            role:['审批供码用户'],
            meta: { title: '审批供码用户' }
          },
          {
            path: 'suppliers',
            name: 'suppliers',
            component: () => import('@/views/userCenter/supplierManage/suppliers/index'),
            role:['所有供码用户'],
            meta: { title: '所有供码用户' }
          }    
        ]
      },
      {
        path: 'merchantManage',
        component: () => import('@/views/userCenter/merchantManage/index'),
        name: 'merchantManage',
        role:['商户管理'],
        meta: { title: '商户管理' },
        children: [
          {
            path: 'addmerchant',
            name: 'addmerchant',  
            component: () => import('@/views/userCenter/merchantManage/addMerchant/index'),
            role:['添加商户'],
            meta: { title: '添加商户' }
          },
          {
            path: 'waitApprovalMer',
            name: 'waitApprovalMer',  
            component: () => import('@/views/userCenter/merchantManage/waitApprovalMer/index'),
            role:['审批商户'],
            meta: { title: '审批商户' }
          },
          {
            path: 'merchants',
            name: 'merchants',
            component: () => import('@/views/userCenter/merchantManage/merchants/index'),
            role:['所有商户'],
            meta: { title: '所有商户' }
          }    
        ]
      },
      {
        path: 'bankCardSetting',
        component: () => import('@/views/userCenter/bankCardSetting/index'),
        name: 'bankCardSetting',
        role:['银行卡设置'],
        meta: { title: '银行卡设置' },
        children: [
          {
            path: 'cards',
            name: 'cards',
            component: () => import('@/views/userCenter/bankCardSetting/cards/index'),
            role:['所有银行卡'],
            meta: { title: '所有银行卡' },
          },
          {
            path: 'cardAdd',
            name: 'cardAdd',
            component: () => import('@/views/userCenter/bankCardSetting/addcard/index'),
            role:['添加银行卡'],
            meta: { title: '添加银行卡', breadcrumb: false },
            breadcrumb: false
          },
          {
            path: 'cardsPersonal',
            name: 'cardsPersonal',
            component: () => import('@/views/userCenter/bankCardSetting/personalcards/index'),
            role:['个人银行卡'],
            meta: { title: '个人银行卡' },
          }
        ]
      },
    ]
  },

  {
    path: '/finance',
    component: Layout,
    redirect: '/finance/receiveCodeList',
    name: 'finance',
    role:['财务管理'],
    meta: {
      title: '财务管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'receiveCodeList',
        component: () => import('@/views/financeManage/receiveCodeList/index'),
        redirect: '/receiveCodeList/codes',
        name: 'receiveCodeList',
        role:['收款码列表'],
        meta: { title: '收款码列表' },
        children: [
          {
            path: 'codes',
            name: 'codes',
            component: () => import('@/views/companyManage/moneyReceiveCode/codes/index'),
            role:['所有收款码'],
            meta: { title: '所有收款码' },
          },
          {
            path: 'codeAdd',
            name: 'codeAdd',
            component: () => import('@/views/companyManage/moneyReceiveCode/codeAdd/index'),
            role:['添加收款码'],
            meta: { title: '添加收款码'},
          }
        ]
      },
      {
        path: 'bankCardList',
        component: () => import('@/views/financeManage/bankCardList/index'),
        name: 'bankCardList',
        role:['银行卡列表'],
        meta: { title: '银行卡列表' }
      },
    ]
  },

  {
    path: '/report',
    redirect: '/report/receiveCodeReport',
    component: Layout,
    name: 'report',
    role:['报表统计'],
    meta: {
      title: '报表统计',
      icon: 'report'
    },
    children: [
      {
        path: 'receiveCodeReport',
        component: () => import('@/views/report/receiveCodeReport/index'),
        name: 'receiveCodeReport',
        role:['收款码报表'],
        meta: { title: '收款码报表' }
      },
      {
        path: 'merchantsReport',
        component: () => import('@/views/report/merchantsReport/index'),
        name: 'merchantsReport',
        role:['商户报表'],
        meta: { title: '商户报表' }
      },
      {
        path: 'localTeamReport',
        component: () => import('@/views/report/localTeamReport/index'),
        name: 'localTeamReport',
        role:['地方团队报表'],
        meta: { title: '地方团队报表' }
      },
      {
        path: 'fundReport',
        component: () => import('@/views/report/fundReport/index'),
        name: 'fundReport',
        role:['资金报表'],
        meta: { title: '资金报表' }
      },
      {
        path: 'agencyReport',
        component: () => import('@/views/report/agencyReport/index'),
        name: 'agencyReport',
        role:['代理报表'],
        meta: { title: '代理报表' }
      }
    ]
  },

  {
    path: '/order',
    redirect: '/order/orderDetails',
    component: Layout,
    name: 'order',
    role:['订单管理'],
    meta: {
      title: '订单管理',
      icon: 'order'
    },
    children: [
      {
        path: 'orderDetails',
        component: () => import('@/views/order/orderDetails/index'),
        name: 'orderDetails',
        role:['订单明细'],
        meta: { title: '订单明细' }
      },
      {
        path: 'withdrawOrder',
        component: () => import('@/views/order/withdrawOrder/index'),
        name: 'withdrawOrder',
        role:['商户提现订单'],
        meta: { title: '商户提现订单' }
      },
      {
        path: 'codeChangeOrder',
        component: () => import('@/views/order/codeChangeOrder/index'),
        name: 'codeChangeOrder',
        role:['内部码帐变订单'],
        meta: { title: '内部码帐变订单' }
      },
      {
        path: 'cardChangeOrder',
        component: () => import('@/views/order/cardChangeOrder/index'),
        name: 'cardChangeOrder',
        role:['内部卡帐变订单'],
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
