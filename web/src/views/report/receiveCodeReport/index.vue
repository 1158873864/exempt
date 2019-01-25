<template>
  <div class="chart-container">
    <!-- <div>商户报表</div> -->
    <!-- <chart height="100%" width="100%"/> -->
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
      <el-table-column prop="alipayLoginId" label="支付宝账号" align="center"></el-table-column>
      <el-table-column prop="date" label="时间" align="center"></el-table-column>
      <el-table-column prop="number" label="编号" align="center"></el-table-column>
      <el-table-column prop="payMoney" label="实付帐款" align="center"></el-table-column>
      <el-table-column prop="supplierName" label="供码用戶名" align="center"></el-table-column>
      <el-table-column prop="withdrew" label="提现金額" align="center"></el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="1000"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import Chart from "@/components/Charts/lineMarker";
import { receiveCodeReport } from "@/api/report";
export default {
  name: "LineChart",
  components: { Chart },
  data() {
    return {
      activeNames: ["1"],
      labelPosition: "right",
      postaddParameters: {
        post: "post"
      },
      teams: [
        {
          alipayLoginId: "string",
          date: "string",
          number: "string",
          payMoney: 0,
          supplierName: "string",
          withdrew: 0
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
        console.log(item.alipayLoginId);
        return !this.searchStr || reg.test(item.alipayLoginId) || reg.test(item.payMoney);
      });
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
      receiveCodeReport().then(response => {
        console.log(response, "sdll");
        if (response.code != 200) {
          this.$message({
            message: response.data.description,
            type: "warning"
          });
        } else {
          if (response.data.length != 0) this.teams = response.data;
          this.teams.forEach(el => {
            el.orderState =
              el.orderState == "WAITING_FOR_PAYING"
                ? "等待支付"
                : "PAID"
                ? "已支付"
                : "失效";
          });
        }
      });
    }
  }
};
</script>

<style scoped>
.chart-container {
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
}
</style>

