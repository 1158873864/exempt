<template>
<div>
    <!-- <el-tag>商户号/代理号</el-tag>
    <el-tag>下发类型(商户还是代理)</el-tag>
    <el-tag>提现金额</el-tag>
    <el-tag>账动前金额</el-tag>
    <el-tag>状态</el-tag>
    <el-tag>申请提现时间</el-tag>
    <el-tag>操作时间</el-tag>
    <el-tag>操作人(抢单人</el-tag>
    <el-tag>哪个财务)</el-tag>
    <el-tag>审核人(财务)</el-tag>
    <el-tag>备注(可以修改)</el-tag> -->

            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="id" label="id"></el-table-column>
            <el-table-column prop="number" label="银行卡号"></el-table-column>
            <el-table-column prop="applicantId" label="订单id"></el-table-column>
            <el-table-column prop="type" label="类型"></el-table-column>
            <el-table-column prop="money" label="提现金额"></el-table-column>
            <el-table-column prop="balance" label="账变前金额"></el-table-column>
            <el-table-column prop="state" label="状态">
                <template scope="scope">
                    <el-tag v-if="scope.row.state=='SUCCESS'" type="success">{{ scope.row.state }}</el-tag>
                    <el-tag v-if="scope.row.state=='FAILED'" type="warning">{{ scope.row.state }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="applyTime" label="申请时间"></el-table-column>
            <el-table-column prop="operateId" label="操作者"></el-table-column>
            <el-table-column prop="cardId" label="卡号"></el-table-column>
            <el-table-column label="操作" align="center">
                <template scope="scope">
                    <el-input v-model="scope.row.memo" name="memo" type="text" auto-complete="on" placeholder="评论" />
                    <el-button size="small"
                            @click="getWithdrew(scope.row.id,scope.row.memo,'SUCCESS')">通过
                    </el-button>
                    <el-button size="small"
                            @click="getWithdrew(scope.row.id,scope.row.memo,'FAILED')">拒绝
                    </el-button>
                </template>
            </el-table-column>
    
        </el-table>
          <div class="block" >
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
</div>

</template>
<script>
  import Table from "../../../components/table/index"
  import {withdrewDeal,withdrewList} from '@/api/transaction'
  import store from '../../../store'
     export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        "id": 1,
                        "cardNumber": "string",
                        "name": "string",
                        "bank": "string",
                        "accountWithBank": "string",
                        "bin": "string",
                        "status": "string",
                        "user": {
                            "id": 2,
                            "username": "string",
                            "role": 3,
                            "tableId": 1,
                            "cards": []
                        }
                        }
                    ],
                    currentPage:1,
                    pagesize:1
                }
            },
            created(){
                this.getData();
            },
            methods: {
                getWithdrew(id,memo,state){
                    withdrewDeal(id,memo,store.getters.uid,state).then(response=>{
                        console.log(response)
                        if(response.infoCode!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                        }else{
                            this.$message({
                            message: '抢单成功',
                            type: 'warning'
                        });
                         this.getData();
                        }
                    })
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
                    withdrewList(store.getters.uid).then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                           this.teams = response.data;
                           this.teams.forEach(el => {
                               el.memo = ''
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
