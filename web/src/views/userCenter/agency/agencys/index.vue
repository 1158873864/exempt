<template>
    <div class="app-container">
    <div>所有代理商</div>
        <el-table
        :data="teams"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="userInfo.username" label="username" width="180" align="center"></el-table-column>
        <el-table-column prop="agentName" label="agentName" width="180" align="center"></el-table-column>
        <el-table-column prop="flow" label="flow" width="180" align="center"></el-table-column>
        <el-table-column prop="dailyCommission" label="dailyCommission" width="180" align="center"></el-table-column>
        <el-table-column prop="status" label="status" width="180" align="center"></el-table-column>
        

    </el-table>
    <div class="block">
        <span class="demonstration">调整每页显示条数</span>
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="10"
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
                    "agentName": "str1ing",
                    "flow": "string",
                    "status": "string",
                    "dailyCommission": 0,
                    "userInfo": {}
                    }
                ],
                currentPage:1
            }
        },
        created(){
            this.getData();
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
              
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
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
            }
        }
    }
</script>

<style scoped>

</style>
