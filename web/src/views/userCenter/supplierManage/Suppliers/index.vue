<template>
  <div class="app-container">
    <div>所有供码用户</div>
    <el-input v-model="searchStr" suffix-icon="el-icon-search" placeholder="请输入搜索内容"></el-input>
    <el-table
      :data="filterData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
      height="450"
      border
      style="width: 100%">
      <el-table-column prop="user.username" label="用户名" align="center"></el-table-column>
      <el-table-column prop="priority" label="等级" align="center"></el-table-column>
      <el-table-column prop="devices_team" label="设备" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="device.online?'success':'info'"
            v-for="device in scope.row.devices"
            :key="device.device_team"
          >{{ device.device_team }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="账号状态" align="center"></el-table-column>
      <el-table-column label="操作" fixed="right" align="center">
        <template scope="scope">
          <el-button size="small" @click="openDialog(scope.$index,scope.row)">修改</el-button>
        </template>
      </el-table-column>
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
    <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
<<<<<<< HEAD
      <el-form :model="newRow">
        <el-form-item label="码类型">
          <el-select v-model="newRow.codeType" placeholder="">
            <el-option label="转账通码" value="TPASS"></el-option>
            <el-option label="转账固码" value="TSOLID"></el-option>
            <el-option label="收款通码离线码" value="RPASSOFF"></el-option>
            <el-option label="收款通码在线码" value="RPASSQR"></el-option>
            <el-option label="收款固码(二开)" value="RSOLID"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="等级">
          <el-input v-model="newRow.level" type="number" min="1" placeholder="请输入等级"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="newRow.user.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="newRow.user.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select v-model="newRow.status" placeholder="">
            <el-option label="启用" value="启用"></el-option>
            <el-option label="停用" value="停用"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateSupplier">确 定</el-button>
      </div>
=======
            <el-form :model="newRow">
                <el-form-item label="码类型">
                    <el-select v-model="newRow.codeType" placeholder="">
                    <el-option label="转账通码" value="TSOLID"></el-option>
                    <el-option label="转账固码" value="TPASS"></el-option>
                    <el-option label="收款通码离线码" value="RSOLID"></el-option>
                    <el-option label="收款通码在线码" value="RPASSOFF"></el-option>
                    <el-option label="收款固码(二开)" value="RPASSQR"></el-option>
                    </el-select>
                </el-form-item>
                 <el-form-item label="状态">
                    <el-select v-model="newRow.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="level">
                    <el-input v-model="newRow.level" type="number" min="1" placeholder="level"></el-input>
                </el-form-item>
                <el-form-item label="password">
                    <el-input v-model="newRow.user.password" type="password" placeholder="password"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSupplier">确 定</el-button>
            </div>
>>>>>>> 1f2a690a3b15e560d8c28879a64f14dcff0f5c7c
    </el-dialog>
  </div>
</template>

<script>
  import {suppliersGet, supplierUpdate} from "@/api/role";

  export default {
    data() {
      return {
        teams: [
          {
            priority: 0,
            devices: [],
            status: "启用",
            user: {
              username: ''
            },
            devices_team: " ",
            username: ''
          }
        ],
        newRow: {
          codeType: "RPASSOFF",
          level: 0,
          password: "",
          user: {},
          priority: 0,
          status: "启用",
        },
        currentPage: 1,
        pagesize: 10,
        newRowIndex: 1,
        dialogFormVisible: false,
        searchStr: "g" // 新增
      };
    },
    computed: {
      filterData() {
        return this.teams.filter(item => {
          var reg = new RegExp(this.searchStr, "i");
          console.log(item.user.username);
          return !this.searchStr || reg.test(item.user.username);
        });
      }
    },
    created() {
      this.getData();
    },
    methods: {
      updateSupplier() {
        supplierUpdate(
<<<<<<< HEAD
            this.newRow.codeType,
            this.newRow.level,
            this.newRow.user.username,
            this.newRow.user.password,
            this.newRow.status,
            this.newRow.id
=======
          this.newRow.codeType,
          this.newRow.level,
          this.newRow.user.password,
          this.newRow.status,
          this.newRow.id
>>>>>>> 1f2a690a3b15e560d8c28879a64f14dcff0f5c7c
        ).then(response => {
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.teams[this.newRowIndex].priority = this.newRow.level;
            this.dialogFormVisible = false;
            this.$message({
              message: "修改成功",
              type: "success"
            });
          }
        });
      },
      openDialog(index, row) {
        this.dialogFormVisible = true;
        // console.log(row)
        this.newRow = row;
        // if(row.codeType==None){
        //     this.newRow.codeType = 'TSOLID'
        // }else{
        //      this.newRow.codeType = row.codeType;
        // }
        this.newRow.level = row.priority;
        // this.newRow.password = row.user.password;
        //this.newRow = JSON.parse(JSON.stringify(row));
        this.newRowIndex = index;
        console.log(this.newRow);
      },
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
        suppliersGet().then(response => {
          console.log(response, "sdll");
          if (response.code != 200) {
            this.$message({
              message: response.data.description,
              type: "warning"
            });
          } else {
            this.teams = response.data;

            this.teams.forEach(el => {
              el.devices.forEach(de => {
                console.log(de.imei);
                de.device_team = de.imei + " " + (de.online ? "在线" : "离线");
              });
            });
          }
        });
      }
    }
  };
</script>

<style scoped>
</style>
