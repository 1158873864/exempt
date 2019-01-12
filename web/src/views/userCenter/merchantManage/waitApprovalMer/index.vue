<template>
        <div class="app-container">
        <div>待审批商户</div>
            <el-table
            :data="teams"
            height="250"
            border
            style="width: 100%">
            <el-table-column prop="addTime" label="addTime" width="180"></el-table-column>
            <el-table-column prop="alipay" label="alipay" width="80"></el-table-column>
            <el-table-column prop="balance" label="balance" width="80"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="name" width="180"></el-table-column>
            <el-table-column prop="status" label="status" width="80"></el-table-column>
            <el-table-column prop="superior" label="superior" width="180"></el-table-column>
            <el-table-column prop="verifyCode" label="verifyCode" width="180"></el-table-column>
            <el-table-column prop="wechat" label="wechat" width="180"></el-table-column>
   
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
      </div>
    </template>
    
    <script>
    import { waitApprovalMer } from '@/api/company'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    
                    teams:[{
                        mid: 'mid',
                        state: 'state'
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
                    waitApprovalMer().then(response=>{
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
    