<template>
        <div class="app-container">
        <div>待审批商户</div>
            <el-table
            :data="teams"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="addTime" label="addTime" width="180" align="center"></el-table-column>
            <el-table-column prop="alipay" label="alipay" width="80" align="center"></el-table-column>
            <el-table-column prop="balance" label="balance" width="80" align="center"></el-table-column>
            <el-table-column prop="name" label="name" width="100" align="center"></el-table-column>
            <el-table-column prop="status" label="status" width="80" align="center"></el-table-column>
            <el-table-column prop="superior" label="superior" width="80" align="center"></el-table-column>
            <el-table-column prop="verifyCode" label="verifyCode" width="100" align="center"></el-table-column>
            <el-table-column prop="wechat" label="wechat" width="100" align="center"></el-table-column>
            <el-table-column label="操作" width="180" align="center">
                    <template scope="scope">
                        <el-button size="small"
                                @click="approval(scope.$index,scope.row,1)">审批通过</el-button>
                        <el-button size="small"
                                @click="approval(scope.$index,scope.row,0)">审批不通过</el-button>
                    </template>
            </el-table-column>
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
    import { waitApprovalMer, ApprovalMer } from '@/api/company'
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
                approval(index, row,state) {
                    console.log(row);
                    ApprovalMer(row.id,state).then(response=> {
                        if(response.data.infoCode){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                          this.$message({
                           message: '审批成功',
                          type: 'success'
                          });
                        }
                       });
                       this.teams.splice(index,1)

                },
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
    