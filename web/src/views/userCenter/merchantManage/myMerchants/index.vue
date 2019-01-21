<template>
    <div class="app-container">
    <div>我的商户</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
        <el-table-column prop="name" label="商户名"  align="center"></el-table-column>
        <el-table-column prop="alipay" label="支付宝点位"  align="center"></el-table-column>
        <el-table-column prop="wechat" label="微信号点位"  align="center"></el-table-column>
        <el-table-column prop="balance" label="余额"  align="center"></el-table-column>
        <el-table-column prop="applyId" label="代理商"  align="center"></el-table-column>
        <el-table-column prop="addTime" label="申请时间"  align="center"></el-table-column>
        <el-table-column prop="status" label="状态"  align="center">
             <template slot-scope="{row}">
            <el-button type="success" size="small" v-if="row.status=='审批通过'">审批通过</el-button>
            <el-tag type="success" v-if="row.status=='审批通过'">{{ row.approvalTime }}</el-tag>
            <el-button type="info" size="small" v-else-if="row.status=='等待审批'">等待审批</el-button>
            <el-button type="warning" size="small" v-else-if="row.status=='审批不通过'">审批不通过</el-button>
             <el-tag type="warning" v-if="row.status=='审批不通过'">{{ row.approvalTime }}</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope" >
                <el-button size="small" 
                        @click="openDialog(scope.$index,scope.row)">修改</el-button>
            </template>
                
        </el-table-column>
        <!-- <el-table-column prop="approvalTime" label="审批时间"  align="center"></el-table-column> -->
        

    </el-table>
    <div class="block">
        <span class="demonstration">调整每页显示条数</span>
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="1000">
        </el-pagination>
    </div>
      <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
            <el-form :model="newRow">
                <el-form-item label="username">
                    <el-input v-model="newRow.user.username" placeholder="area"></el-input>
                </el-form-item>
                <el-form-item label="password">
                    <el-input v-model="newRow.user.password" placeholder="password"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSupplier">确 定</el-button>
            </div>
    </el-dialog>
  </div>
</template>

<script>
import { merchantsMy,updateMerchant } from '@/api/role'
import store from '../../../../store'
import getTime from '@/utils/index'
    export default {
        data() {
            return {
                teams:[{
                    "addTime": "2019-01-17T16:37:02.184Z",	//申请时间
                    "alipay": 0,	
                    "applyId": 0,	//申请人id
                    "approvalTime": "2019-01-17T16:37:02.184Z",	//审批时间
                    "approverId": 0,	//审批人id
                    "balance": 0,
                    "id": 0,
                    "name": "string",
                    "priority": 0,	//用户星级
                    "status": "WAITING",	//WAITING 等待审批/PASS 审批通过/ REJECT 审批不通过
                    "user": {
                        "avatarUrl": "string",
                        "cards": [
                        {
                            "accountWithBank": "string",
                            "bank": "string",
                            "bin": "string",
                            "cardNumber": "string",
                            "id": 0,
                            "name": "string",
                            "status": "string"
                        }
                        ],
                        "id": 0,
                        "password": "string",
                        "role": 0,
                        "tableId": 0,
                        "username": "string"
                    },
                    "verifyCode": "string",
                    "wechat": 0
                    }
                ],
                currentPage:1,
                pagesize:10,
                newRow: {
                    "codeType": "",
                    "level": 0,
                    "password": "",
                    "user": {}
                    },
                newRowIndex:1,
                dialogFormVisible: false
            }
        },
        created(){
            this.getData();
        },
        methods: {
             updateSupplier() {
                updateMerchant(this.newRow.id,this.newRow.user.username,this.newRow.user.password).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        // this.teams[this.newRowIndex].priority = this.newRow.level;
                        this.dialogFormVisible = false;
                         this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                });
            },
             openDialog(index,row) {
                this.dialogFormVisible=true;
                //this.newRow = JSON.parse(JSON.stringify(row));
                this.newRow = row;

            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pagesize=val;
              
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage=val;
            },
            getData(){
                this.getTeams();
            },
            getTeams(){
                merchantsMy(store.getters.uid).then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                       this.teams.forEach(el => {
                           el.status=el.status=='WAITING'?'等待审批':'PASS'?'审批通过':'审批不通过';
                           el.wechat = el.wechat+'%';
                           el.alipay = el.alipay+'%';
                           el.addTime = getTime(el.addTime)
                        //    el.approvalTime = getTime(el.approvalTime)
                       });
                    }
                })
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getTeams();
                }
            }
        }
    }
</script>

<style scoped>

</style>
