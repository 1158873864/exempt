<template>
    <div class="app-container">
    <div>所有代理商</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="userInfo.username" label="用户名" align="center"></el-table-column>
        <el-table-column prop="name" label="代理名"  align="center"></el-table-column>
        <el-table-column prop="dailyFlow" label="流量"  align="center"></el-table-column>
        <el-table-column prop="dailyCommission" label="当日佣金"  align="center"></el-table-column>
        <el-table-column prop="status" label="状态"  align="center"></el-table-column>
        <el-table-column label="操作" fixed="right" align="center" >
            <template scope="scope" >
                <el-button size="small" 
                        @click="openDialog(scope.$index,scope.row)">修改</el-button>
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
        :total="1000">
        </el-pagination>
    </div>
  </div>
</template>

<script>
import { agentsGet } from '@/api/role'
    export default {
        data() {
            return {
                teams:[{
                id: 1,
                agentName: "a1",
                status: "string",
                percent: 0.3,
                balance: 0,
                userInfo: {
                    id: 29,
                    username: "a1",
                    password: "$2a$10$dJ/s9iiGBGzQS/7XbSYUMODsSzblY0X3Zu7GKdjvKrKuHoaCc5Q6a",
                    role: 2,
                    tableId: 1,
                    cards: []
                }
                }
                ],
                currentPage:1,
                pagesize:10,
                                dialogFormVisible: false,
                searchStr: '', // 新增
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
                agentsGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                    }
                })
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getTeams();
                }
            },
            openDialog(index,row) {
                this.dialogFormVisible=true;
                //this.newRow = JSON.parse(JSON.stringify(row));
                this.newRow = row;
                this.newRow.level = row.priority;

            },
        }
    }
</script>

<style scoped>

</style>
