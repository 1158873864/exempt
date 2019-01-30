<template>
  <div class="chart-container">
    <el-input
      v-model="searchStr"
      style="width:30vw;margin:20px 0 20px 0;"
      suffix-icon="el-icon-search"
      placeholder="请输入搜索内容"
    ></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="500"
      border
      style="width: 100%"
    >
      <el-table-column prop="cardNumber_in" label="银行卡转入" align="center"></el-table-column>
      <el-table-column prop="cardNumber_out" label="银行卡转出" align="center"></el-table-column>
      <el-table-column prop="money" label="银行卡转帐金額" align="center"></el-table-column>
      <el-table-column prop="operateId" label="操作人" align="center"></el-table-column>
    
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total=total
      ></el-pagination>
    </div>
  </div>
</template>

<script>
// import Chart from '@/components/Charts/lineMarker'
import { Pcard,Ccard } from "@/api/role";
export default {
  name: "index",
  //   components: { Chart },
  data() {
    return {
      activeNames: ["1"],
      labelPosition: "right",
      postaddParameters: {
        post: "post"
      },
      teams: [
        {
          cardNumber_in: "string",
          cardNumber_out: "string",
          money: 0,
          operateId: 0
        }
      ],
      currentPage: 1,
      pagesize: 10,
      searchStr: ""
    };
  },
  computed: {
    filterData() {
      return this.teams.filter(item => {
        var reg = new RegExp(this.searchStr, "i");
        console.log(item.cardNumber_in);
        return (
          !this.searchStr ||
          reg.test(item.cardNumber_in) ||
          reg.test(item.money)
        );
      });
    },
    total(){
      return this.teams.length;
    }
  },
  created() {
    this.getData();
  },
  methods: {
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pagesize = val;
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val;
    },
    getData() {
      this.getTeams();
    },
   getTeams() {
      Pcard().then(response => {
        console.log(response, "sdll");
        if (response.code != 200) {
          this.$message({
            message: response.data.description,
            type: "warning"
          });
        } else {
          if (response.data.length != 0) {
            this.teams = response.data;
            this.teams.forEach(el => {
              el.time = getTime(el.time);
              el.approvalTime = getTime(el.approvalTime);
              el.payTime = getTime(el.payTime);
            });
          }
        }
      });
    }
  }
};
</script>

<style scoped>
/* .chart-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
} */
</style>


