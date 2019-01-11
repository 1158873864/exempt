<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-collapse v-model="activeNames" @change="handleChange">
            <el-collapse-item title="添加团队" name="1">
                <el-form :label-position="labelPosition" :model="teamAddParameters" class="demo-form-inline">
                    <el-form-item label="area">
                        <el-input v-model="teamAddParameters.area" placeholder="area"></el-input>
                    </el-form-item>
                    <el-form-item label="operator">
                        <el-input v-model="teamAddParameters.operator" placeholder="operator"></el-input>
                    </el-form-item>
                    <el-form-item label="status">
                        <el-input v-model="teamAddParameters.status" placeholder="status"></el-input>
                    </el-form-item>
                    <el-form-item label="supervisor">
                        <el-input v-model="teamAddParameters.supervisor" placeholder="supervisor"></el-input>
                    </el-form-item>
                    <el-form-item label="teamName">
                        <el-input v-model="teamAddParameters.teamName" placeholder="teamName"></el-input>
                    </el-form-item>
                    <el-form-item label="verifyCode">
                        <el-input v-model="teamAddParameters.verifyCode" placeholder="verifyCode"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="addteam">添加</el-button>
                    </el-form-item>
                </el-form>
            </el-collapse-item>
            <el-collapse-item  title="查看所有团队" name="2" @click="getTeams">
                  <el-table
                    :data="teams"
                    height="250"
                    border
                    style="width: 100%">
                    <el-table-column prop="teamName" label="teamName" width="180"></el-table-column>
                    <el-table-column prop="addTime" label="addTime" width="180"></el-table-column>
                    <el-table-column prop="area" label="area" width="180"></el-table-column>
                    <el-table-column prop="id" label="id" width="180"></el-table-column>
                    <el-table-column prop="operator" label="operator" width="180"></el-table-column>
                    <el-table-column prop="status" label="status" width="180"></el-table-column>
                    <el-table-column prop="supervisor" label="supervisor" width="180"></el-table-column>
                    <el-table-column prop="verifyCode" label="verifyCode" width="180"></el-table-column>

                </el-table>
                <div class="block">
                    <span class="demonstration">调整每页显示条数</span>
                    <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page.sync="currentPage"
                    :page-sizes="[100, 200, 300, 400]"
                    :page-size="100"
                    layout="sizes, prev, pager, next"
                    :total="1000">
                    </el-pagination>
                </div>
            </el-collapse-item>
        </el-collapse>
        
  </div>
</template>

<script>
import { teamAdd,teamsGet } from '@/api/team'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                teamAddParameters: {
                        "area": "area",
                        "operator": "operator",
                        "status": "status",
                        "supervisor": "supervisor",
                        "teamName": "teamName",
                        "verifyCode": "verifyCode"
                },
                teams:{},
                currentPage:1
            }
        },
        created(){
            // this.getData();
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
                teamsGet().then(response=>{
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
            addteam() {
                teamAdd(this.teamAddParameters.area,this.teamAddParameters.operator,this.teamAddParameters.status,this.teamAddParameters.supervisor,this.teamAddParameters.teamName,this.teamAddParameters.verifyCode).then(response=>{
                    if(response.data.infoCode){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.$message({
                            message: '添加成功',
                            type: 'success'
                        });
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
