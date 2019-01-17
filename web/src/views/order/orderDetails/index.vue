<template>
        <div class="app-container">
            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="id" label="订单编号"  align="center"></el-table-column>
            <el-table-column prop="money" label="订单金额"  align="center"></el-table-column>
            <el-table-column prop="payMoney" label="成交金额"  align="center"></el-table-column>
            <el-table-column prop="rechargeId" label="充值方编号"  align="center"></el-table-column>
            <el-table-column prop="payCode" label="收款码"  align="center"></el-table-column>
            <el-table-column prop="state" label="状态"  align="center"></el-table-column>
            <el-table-column prop="time" label="时间"  align="center"></el-table-column>
            <el-table-column prop="number" label="号码"  align="center"></el-table-column>
            <el-table-column prop="ip" label="ip"  align="center"></el-table-column>
            <!-- <el-table-column prop="uid" label="uid"  align="center"></el-table-column> -->
            <el-table-column prop="imei" label="imei"  align="center"></el-table-column>


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
                            if(response.data.length!=0)
                                this.teams = response.data;
                        }
                    })
                },

            }
        }
    </script>

    <style scoped>

    </style>
