<template>
  <div class="chart-container">
      <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
     <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="orderNumber" label="系统编号"  align="center"></el-table-column>
            <el-table-column prop="time" label="日期"  align="center"></el-table-column>
            <el-table-column prop="merchantId" label="商户号"  align="center"></el-table-column>
            <el-table-column prop="money" label="存款"  align="center"></el-table-column>
            <el-table-column prop="nickname" label="支付宝昵称"  align="center"></el-table-column>
            <el-table-column prop="rechargeId" label="充值方编号"  align="center"></el-table-column>
            <el-table-column prop="merchantName" label="商户名"  align="center"></el-table-column>
            <!-- <el-table-column prop="orderId" label="orderId"  align="center"></el-table-column> -->
            <el-table-column prop="orderState" label="支付状态"  align="center"></el-table-column>
            <el-table-column prop="payTime" label="支付时间"  align="center"></el-table-column>
            <el-table-column prop="paymoney" label="支付金额"  align="center"></el-table-column>
   

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
import Chart from '@/components/Charts/lineMarker'
import {merchantsReport,getPermerchantReport} from '@/api/report'
export default {
  name: 'LineChart',
  components: { Chart },
  data() {
          return {
              activeNames: ['1'],
              labelPosition: 'right',
              postaddParameters: {
                      "post": "post",
              },
              teams:[{
                    code: "string",
                    merchantId: 0,
                    merchantName: "string",
                    money: 0,
                    orderId: 0,
                    orderNumber: "string",
                    orderState: "WAITING_FOR_PAYING",
                    payTime: "2019-01-18T05:34:14.271Z",
                    paymoney: 0,
                    rechargeId: "string",
                    time: "2019-01-18T05:34:14.271Z"
                      }],
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
            merchantsReport().then(response=>{
                console.log(response,'sdll')
                  if(response.code!=200){
                    this.$message({
                        message: response.data.description,
                        type: 'warning'
                    });
                }else{
                  if(response.data.length!=0)
                    this.teams = response.data;
                    this.teams.forEach(el => {
                        el.orderState = (el.orderState=='WAITING_FOR_PAYING')?'等待支付':'PAID'?'已支付':'失效';
                    });
                }
            })
          },
    }
}
</script>

<style scoped>
.chart-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
}
</style>

