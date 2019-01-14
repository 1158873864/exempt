<template>
    <div class="app-container">
    <div>所有商户</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="user.username" label="username" width="180" align="center"></el-table-column>
        <el-table-column prop="name" label="merchantName" width="180" align="center"></el-table-column>
        <el-table-column prop="alipay" label="alipay" width="180" align="center"></el-table-column>
        <el-table-column prop="wechat" label="wechat" width="180" align="center"></el-table-column>
        <el-table-column prop="balance" label="balance" width="80" align="center"></el-table-column>
        <el-table-column prop="superior" label="superior" width="180" align="center"></el-table-column>
        <el-table-column prop="status" label="status" width="180" align="center"></el-table-column>
        

    </el-table>
    <div class="block">
        <span class="demonstration">调整每页显示条数</span>
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
import { merchantsGet } from '@/api/role'
    export default {
        data() {
            return {
                teams:[{
                    "alipay": "string",
                    "wechat": "string",
                    "balance": 0,
                    "status": "WAITING",
                    "name": "string",
                    "superior": "1",
                    "user": {}
                    }
                ],
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
                merchantsGet().then(response=>{
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
