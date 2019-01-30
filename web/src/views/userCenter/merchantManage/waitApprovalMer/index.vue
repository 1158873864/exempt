<template>
        <div class="app-container">
        <div>待审批商户</div>
            <el-table
            :data="teams"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="alipay" label="支付宝点位"  align="center"></el-table-column>
            <el-table-column prop="approverId" label="审核人id"  align="center"></el-table-column>
            <el-table-column prop="priority" label="等级"  align="center"></el-table-column>
            <el-table-column prop="user.password" label="密码"  align="center"></el-table-column>
            <el-table-column prop="status" label="状态"  align="center"></el-table-column>
            <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
            <el-table-column prop="wechat" label="微信点位"  align="center"></el-table-column>
            <el-table-column prop="mid" label="商户id"  align="center"></el-table-column>
            <el-table-column label="操作" width="280" align="center">
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
            :total=total>
            </el-pagination>
        </div>
      </div>
    </template>
    
    <script>
    import { waitApprovalMer, ApprovalMer } from '@/api/company'
    import store from '../../../../store'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    
                    teams:[{
                        alipay:0,
                        approverId:0,
                        level:0,
                        password:0,
                        status:0,
                        username:0,
                        wechat:0,
                        mid:0,
                        }
                    ],
                    currentPage:1
                }
            },
            computed:{
                total(){
                    return this.teams.length;
                }
            },
            created(){
                this.getData();
                // this.
            },
            methods: {
                approval(index, row,status) {
                    // console.log(row);
                    ApprovalMer(
                            row.alipay,
                            row.approverId,
                            row.priority,
                            row.user.password,
                            status,
                            row.user.username,
                            row.wechat,
                            row.mid).then(response=> {
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
                           this.teams.forEach(el => {
                               el.approverId = store.getters.uid;
                               el.mid = el.id;
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
    