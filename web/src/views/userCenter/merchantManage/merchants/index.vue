<template>
    <div class="app-container">
    <div>所有商户</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
        <el-table-column prop="name" label="商户名"  align="center"></el-table-column>
        <el-table-column prop="alipay" label="支付宝账号"  align="center"></el-table-column>
        <el-table-column prop="wechat" label="微信号"  align="center"></el-table-column>
        <el-table-column prop="balance" label="余额"  align="center"></el-table-column>
        <el-table-column prop="superior" label="代理商"  align="center"></el-table-column>
        <el-table-column prop="status" label="状态"  align="center"></el-table-column>
        

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
