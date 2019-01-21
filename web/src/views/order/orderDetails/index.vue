<template>
        <div class="app-container">
            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="orderNumber" label="订单编号"  align="center"></el-table-column>
            <el-table-column prop="money" label="订单金额"  align="center"></el-table-column>
            <el-table-column prop="paymoney" label="成交金额"  align="center"></el-table-column>
            <el-table-column prop="rechargeId" label="充值方编号"  align="center"></el-table-column>
            <el-table-column prop="nickname" label="支付宝昵称"  align="center"></el-table-column>
            <!-- <el-table-column prop="code" label="收款码"  align="center"></el-table-column> -->
            <el-table-column prop="time" label="订单时间"  align="center"></el-table-column>
            <el-table-column prop="orderState" label="状态"  align="center">
                 <template slot-scope="{row}">
                    <el-button type="success" size="small" v-if="row.orderState=='PAID'">已支付</el-button>
                    <el-tag type="success" v-if="row.orderState=='审批通过'">{{ row.approvalTime }}</el-tag>
                    <el-button type="info" size="small" v-else-if="row.orderState=='WAITING_FOR_PAYING'">等待审批</el-button>
                    <el-button type="warning" size="small" v-else-if="row.orderState=='EXPIRED'">审批不通过</el-button>
                    <el-tag type="warning" v-if="row.orderState=='PAID'">{{ row.payTime }}</el-tag>
                </template>
            </el-table-column>
            <!-- <el-table-column prop="payTime" label="到账时间"  align="center"></el-table-column> -->
            <!-- <el-table-column prop="ip" label="ip"  align="center"></el-table-column> -->
            <!-- <el-table-column prop="uid" label="uid"  align="center"></el-table-column> -->
            <el-table-column prop="merchantName" label="商户"  align="center"></el-table-column>


        </el-table>
        <div class="block">
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
    import { ordersGet } from '@/api/order'
    import { getTime } from '@/utils/index'
        export default {
            data() {
                return {
                    teams:[{
                        "id": 1,
                        "number": "dfadfas",
                        "state": "WAITING_FOR_PAYING",
                        "payCode": "sadfsadf",
                        "ip": "10.30.256.106",
                        "rechargeId": "12",
                        "money": 1,
                        "payMoney": 0,
                        "uid": 1,
                        "imei": "12222222",
                        "time":"2019-01-17T04:58:07.882Z"
                        }
                    ],
                    currentPage:1,
                    pagesize:10
                }
            },
            created(){
                this.getData();
            },
            methods: {
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
                    ordersGet().then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            if(response.data.length!=0){
                                this.teams = response.data;
                                this.teams.forEach(el => {
                                   el.time = getTime(el.time)
                                   el.approvalTime = getTime(el.approvalTime)
                                   el.payTime = getTime(el.payTime)
                               })
                            }
                        }
                    })
                },

            }
        }
    </script>

    <style scoped>

    </style>
