<template>
  <div class="chart-container">
      <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
    <el-input
      v-model="searchStr"
      style="width:30vw;margin:20px 20px 20px 20px;"
      suffix-icon="el-icon-search"
      placeholder="请输入搜索内容"
    ></el-input>
     <el-date-picker v-model="startDate" type="date"  @change="startDateChange"   placeholder="起始日期" style="margin:20px 20px 20px 20px;"></el-date-picker>
     <el-date-picker v-model="endDate" type="date"  @change="endDateChange"   placeholder="截止日期" style="margin:20px 20px 20px 20px;"></el-date-picker>
     <el-button type="primary" @click="dateSearch">查询</el-button>
     <el-table
            :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="500"
            border
            style="width: 100%">
            <el-table-column prop="number" label="编号"  align="center"></el-table-column>
            <el-table-column prop="supplierToCom" label="供码用户转入公司金额"  align="center"></el-table-column>
            <el-table-column prop="comToAgent" label="公司转入代理商金额"  align="center"></el-table-column>
            <el-table-column prop="comToMerchant" label="公司转入商户金额"  align="center"></el-table-column>
            <el-table-column prop="date" label="日期"  align="center"></el-table-column>
            

        </el-table>
           <div class="block">
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pagesize"
            layout="sizes, prev, pager, next"
            :total=total>
            </el-pagination>
        </div>
  </div>
</template>

<script>
import Chart from '@/components/Charts/lineMarker'
import { fundingReport} from '@/api/report'
import { getTime,getTimeFormat } from "@/utils/index";
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
                    comToAgent: 0,
                    comToMerchant: 0,
                    date: "string",
                    number: "string",
                    supplierToCom: 0
                      }],
                currentPage:1,
                pagesize:10,
                searchStr:'',
                startDate:"",
                endDate:""
          }
      },
    computed: {
        filterData() {
        return this.teams.filter(item => {
            var reg = new RegExp(this.searchStr, "i");
            console.log(item.supplierToCom);
            return !this.searchStr || reg.test(item.supplierToCom) || reg.test(item.comToAgent)||reg.test(comToMerchant);
        });
        },
        total(){
            return this.teams.length;
        }
    },
    created(){
        this.getData();
    },
    methods: {
            startDateChange(val){
            this.startDate = val;
            },
            endDateChange(val){
            this.endDate = val;
            },
            dateSearch(){
                fundingReport(getTimeFormat(this.startDate),getTimeFormat(this.endDate)).then(response=>{
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
                        //  el.orderState = (el.orderState=='WAITING_FOR_PAYING')?'等待支付':'PAID'?'已支付':'失效';
                        // el.date = getTime(el.date);
                    });
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
            fundingReport("2000-01-01",getTimeFormat(new Date())).then(response=>{
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
            });
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

