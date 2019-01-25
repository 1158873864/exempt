<template>
  <div class="chart-container">
      <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
     <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="date" label="date"  align="center"></el-table-column>
            <el-table-column prop="number" label="number"  align="center"></el-table-column>
            <el-table-column prop="realReceipt" label="realReceipt"  align="center"></el-table-column>
            <el-table-column prop="supplierName" label="supplierName"  align="center"></el-table-column>
            <el-table-column prop="withdrew" label="withdrew"  align="center"></el-table-column>
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
import { supplierReport} from '@/api/report'
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
                      date: "string",
                      number: "string",
                      realReceipt: 0,
                      supplierName: "string",
                      withdrew: 0
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
            supplierReport().then(response=>{
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

