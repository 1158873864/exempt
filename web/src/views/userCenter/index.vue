<template>
    <div>
        <el-card v-if="userInfo.role==1" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
            </div>
            <!-- <div  class="text item">{{ "id:" + info.id }},</div> -->
            <div  class="text item">{{ '用户名: ' + userInfo.username }}</div>
            <div  class="text item">{{ '所属团队: ' + info.team }}</div>
            <div  class="text item">{{ '添加时间: ' + info.addTime }}</div>
            <!-- <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'operator:' + info.operator }}</div> -->
            <div  class="text item">{{ '状态: ' + info.status }}</div>
            <div  class="text item">{{ '岗位: ' + info.post }}</div>
            <div  class="text item">{{ '角色: ' + userInfo.role }}</div>
        </el-card>
         <el-card v-if="userInfo.role==4" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <!-- <div  class="text item">{{ "id:" + info.id }},</div>
            <div  class="text item">{{ 'alipay:' + info.alipay }}</div>
            <div  class="text item">{{ 'wechat:' + info.wechat }}</div> -->
            <div  class="text item">{{ '用户名: ' + userInfo.username }}</div>
            <div  class="text item">{{ '添加时间: ' + info.time }}</div>
            <div  class="text item">{{ '审批时间: ' + info.approvalTime }}</div>
            <!-- <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'operator:' + info.operator }}</div> -->
            <div  class="text item">{{ '状态: ' + info.status }}</div>
            <div  class="text item">{{ '角色: ' + userInfo.role }}</div>
        </el-card>
                <el-card v-if="userInfo.role==2" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <!-- <div  class="text item">{{ "id:" + info.id }},</div>
            <div  class="text item">{{ 'alipay:' + info.alipay }}</div>
            <div  class="text item">{{ 'wechat:' + info.wechat }}</div> -->
            <div  class="text item">{{ '用户名: ' + userInfo.username }}</div>
            <!-- <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'operator:' + info.operator }}</div> -->
            <div  class="text item">{{ '状态: ' + info.status }}</div>
            <div  class="text item">{{ '角色: ' + userInfo.role }}</div>
            <el-form>
                <el-form-item>
                    {{ '余额:' + info.balance }}
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog">提现</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card v-if="userInfo.role==3" class="box-card">
            <div slot="header" class="clearfix">
                <span>个人信息</span>
                <!-- <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button> -->
            </div>
            <!-- <div class="text item"> -->
                <!-- {{'列表内容 ' + o }} -->
            <!-- <div  class="text item">{{ "id:" + info.id }},</div>
            <div  class="text item">{{ 'alipay:' + info.alipay }}</div>
            <div  class="text item">{{ 'wechat:' + info.wechat }}</div> -->
            <div  class="text item">{{ '用户名: ' + userInfo.username }}</div>
            <div  class="text item">{{ '添加时间: ' + info.addTime }}</div>
            <!-- <div  class="text item">{{ 'verifyCode:' + info.verifyCode }}</div>
            <div  class="text item">{{ 'operator:' + info.operator }}</div> -->
            <div  class="text item">{{ '状态: ' + info.status }}</div>
            <div  class="text item">{{ '角色: ' + userInfo.role }}</div>
            <el-form>
                <el-form-item>
                    {{ '余额:' + info.balance }}
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="openDialog">提现</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-table :data="list" style="width: 100%;padding-top: 15px;">
            <!-- <el-table-column label="id" min-width="200" prop="id"></el-table-column> -->
            <el-table-column label="卡号" min-width="200" prop="cardNumber"></el-table-column>
            <el-table-column label="姓名" min-width="200" prop="name"></el-table-column>
            <el-table-column label="银行" min-width="200" prop="bank"></el-table-column>
            <el-table-column label="开户行" min-width="200" prop="accountWithBank"></el-table-column>
            <el-table-column label="银行编号" min-width="200" prop="bin"></el-table-column>
            <el-table-column label="状态" min-width="200" prop="status"></el-table-column>
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
 <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
            <el-form :model="newRow">
                <!-- <el-form-item label="cardId">
                    <el-input v-model="newRow.cardId" placeholder="area"></el-input>
                </el-form-item> -->
                <!-- <el-form-item label="id">
                    <el-input v-model="newRow.id" placeholder="area"></el-input>
                </el-form-item> -->
                <el-form-item label="取款数">
                    <el-input v-model="newRow.money" placeholder="area"></el-input>
                </el-form-item>
                <!-- <el-form-item label="type">
                    <el-input v-model="newRow.type" placeholder="area"></el-input>
                </el-form-item> -->
                <el-form-item label="提现到卡号">
                    <el-select v-model="newRow.cardId" placeholder="">
                    <el-option v-for="card in list" :key="card.id" :label="card.cardNumber" :value="card.cardNumber"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="getMoney">确 定</el-button>
            </div>
    </el-dialog>
    </div>
</template>

<script>
import { getInfo } from '@/api/login'
import store from '../../store'
import { withdrew } from '@/api/transaction'
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
        userInfo:{},
        newRowIndex:1,
        dialogFormVisible: false,
        newRow:{
            cardId: 0,
            id: 0,
            money: 0,
            type: "string"
        }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    openDialog() {
        this.dialogFormVisible=true;
        this.newRow.type = this.userInfo.role==2?'agent':'merchant';
        this.newRow.id = store.getters.uid;
        console.log('opendialog')
        //this.newRow = JSON.parse(JSON.stringify(row));
            },
    fetchData() {
      getInfo(store.getters.uid).then(response => {
    //   getInfo(33).then(response => {
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
    },
    getMoney(){
        console.log('get money',this.newRow)
        withdrew(
            this.newRow.cardId,
            this.newRow.id,
            // 33,
            this.newRow.money,
            this.newRow.type).then(res =>{
                    if(res.code!=200){
                        this.$message({
                            message: res.data.description,
                            type: 'warning'
                        });
                    }else{
                        
                         this.$message({
                            message: '提交提现申请成功',
                            type: 'success'
                        });
                    }
            })
            this.dialogFormVisible=false;
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
