<template>
  <div class="chart-container">
      <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
     <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="orderMoney" label="订单金额"  align="center"></el-table-column>
            <el-table-column prop="payMoney" label="已付金额"  align="center"></el-table-column>
            <el-table-column prop="time" label="时间"  align="center"></el-table-column>
            <!-- <el-table-column prop="payCode" label="支付码"  align="center"></el-table-column>
            <el-table-column prop="ip" label="ip"  align="center"></el-table-column>
            <el-table-column prop="rechargeId" label="rechargeId"  align="center"></el-table-column>
            <el-table-column prop="money" label="订单金额"  align="center"></el-table-column>
            <el-table-column prop="payMoney" label="支付金额"  align="center"></el-table-column>
            <el-table-column prop="uid" label="uid"  align="center"></el-table-column>
            <el-table-column prop="imei" label="imei"  align="center"></el-table-column> -->


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
                      "orderMoney": 0,
                      "payMoney": 0,
                      "time": "2019-01-17T04:58:07.905Z"
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
                    this.teams[0] = response.data;
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

