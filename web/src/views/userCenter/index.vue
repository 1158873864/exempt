<template>
    <div>
        <el-card v-if="userInfo.role==1" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <div  class="text item">{{ "id:" + info.id }},</div>
            <div  class="text item">{{ 'staffName:' + info.staffName }}</div>
            <div  class="text item">{{ 'team:' + info.team }}</div>
            <div  class="text item">{{ 'addTime:' + info.addTime }}</div>
            <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'operator:' + info.operator }}</div>
            <div  class="text item">{{ 'status:' + info.status }}</div>
            <div  class="text item">{{ 'post:' + info.post }}</div>
            <div  class="text item">{{ 'role: ' + userInfo.role }}</div>
            <!-- </div> -->
        </el-card>
        <el-card v-if="userInfo.role==2" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <div  class="text item">{{ "agentName: " + info.agentName }},</div>
            <div  class="text item">{{ 'balance: ' + info.balance }}</div>
            <div  class="text item">{{ 'id: ' + info.id }}</div>
            <div  class="text item">{{ 'percent: ' + info.percent }}</div>
            <div  class="text item">{{ 'status: ' + info.status }}</div>
            <div  class="text item">{{ 'role: ' + userInfo.role }}</div>
            <!-- </div> -->
        </el-card>
        <el-card v-if="userInfo.role==3||userInfo.role==4" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <div  class="text item">{{ "id:" + info.id }},</div>
            <div  class="text item">{{ 'alipay:' + info.alipay }}</div>
            <div  class="text item">{{ 'wechat:' + info.wechat }}</div>
            <div  class="text item">{{ 'balance:' + info.balance }}</div>
            <div  class="text item">{{ 'status:' + info.status }}</div>
            <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'addTime:' + info.addTime }}</div>
            <div  class="text item">{{ 'name:' + info.name }}</div>
            <div  class="text item">{{ 'applyId:' + info.applyId }}</div>
            <div  class="text item">{{ 'approverId:' + info.approverId }}</div>
            <div  class="text item">{{ 'approvalTime:' + info.approvalTime }}</div>
            <div  class="text item">{{ 'priority:' + info.priority }}</div>
            <div  class="text item">{{ 'withdrewMoney:' + info.withdrewMoney }}</div>
            <div  class="text item">{{ 'role: ' + userInfo.role }}</div>
            <!-- </div> -->
        </el-card>
        <el-table :data="list" style="width: 100%;padding-top: 15px;">
            <el-table-column label="id" min-width="200" prop="id"></el-table-column>
            <el-table-column label="cardNumber" min-width="200" prop="cardNumber"></el-table-column>
            <el-table-column label="name" min-width="200" prop="name"></el-table-column>
            <el-table-column label="bank" min-width="200" prop="bank"></el-table-column>
            <el-table-column label="accountWithBank" min-width="200" prop="accountWithBank"></el-table-column>
            <el-table-column label="bin" min-width="200" prop="bin"></el-table-column>
            <el-table-column label="status" min-width="200" prop="status"></el-table-column>
        <!-- </el-table-column>
            <template slot-scope="scope">
                ¥{{ scope.row.price | toThousandFilter }}
            </template>
            </el-table-column>
            <el-table-column label="Status" width="100" align="center">
            <template slot-scope="scope">
                <el-tag :type="scope.row.status | statusFilter"> {{ scope.row.status }}</el-tag>
            </template>
            </el-table-column> -->
        </el-table>

    </div>
</template>

<script>
import { getInfo } from '@/api/login'
import store from '../../store'
// getInfo(uid)
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      list: null,
      info:{},
      userInfo:{}
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
    //   getInfo(store.getters.uid).then(response => {
      getInfo(29).then(response => {
        if(response.data.info.userInfo)
        {
            this.userInfo  = response.data.info.userInfo
        }
        else
        {
            this.userInfo  = response.data.info.user
        }
            this.list = this.userInfo.cards;
        this.info = response.data.info;
        console.log(this.userInfo)
      })
    }
  }
}
</script>
<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }
</style>
