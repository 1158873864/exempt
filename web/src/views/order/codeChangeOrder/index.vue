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
      <el-table-column prop="cardNumber" label="银行卡号" align="center"></el-table-column>
      <el-table-column prop="loginId" label="支付宝账号" align="center"></el-table-column>
      <el-table-column prop="money" label="支付宝提现金额" align="center"></el-table-column>
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
import { qrcode } from "@/api/role";
export default {
  name: "index",
  data() {
    return {
      activeNames: ["1"],
      labelPosition: "right",
      postaddParameters: {
        post: "post"
      },
      teams: [
        {
          cardNumber: "string1",
          loginId: "string1",
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
        console.log(item.operateId);
        return (
          !this.searchStr || reg.test(item.operateId) || reg.test(item.money)
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
      qrcode().then(response => {
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
</style>


