<template>
        <div class="app-container">
        <div>待审批供码用户</div>
            <el-table
            :data="teams"
            height="550"
            border
            style="width: 100%">
            <el-table-column prop="alipayName" label="alipayName" width="180"></el-table-column>
            <el-table-column prop="alipayUserId" label="alipayUserId" width="80"></el-table-column>

            <el-table-column label='设备'>
               <el-table-column prop="devices.id" label="id" width="80"></el-table-column>
               <el-table-column prop="devices.imei" label="imei" width="80"></el-table-column>

            </el-table-column>   
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="loginId" label="loginId" width="180"></el-table-column>
            <el-table-column prop="status" label="status" width="80"></el-table-column>
            <el-table-column prop="time" label="time" width="180"></el-table-column>
            <el-table-column label="user">
                <el-table-column prop="user.avatarUrl" label="avatarUrl" width="80"></el-table-column>
                <el-table-column prop="user.id" label="id" width="80"></el-table-column>
                <el-table-column prop="user.password" label="password" width="80"></el-table-column>
                <el-table-column prop="user.role" label="role" width="80"></el-table-column>
                <el-table-column prop="user.tableId" label="tableId" width="80"></el-table-column>
                <el-table-column prop="user.username" label="username" width="80"></el-table-column>
                
            </el-table-column>
            <el-table-column label="操作" width="180">
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
            :page-sizes="[100, 200, 300, 400]"
            :page-size="100"
            layout="sizes, prev, pager, next"
            :total="1000">
            </el-pagination>
        </div>
      </div>
    </template>
    
    <script>
    import { waitApprovalSup, ApprovalSup } from '@/api/company'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    
                    teams:[{
                        alipayName: 'alipayName',
                        alipayUserId:   'alipayUserId',
                        devices:[{
                            id: 'id',
                            imei: 'imei'
                        }],
                        id: 'id',
                        loginId: 'loginId',
                        status: 'status',
                        time: 'time',
                        user:[{
                            avatarUrl: 'avatarUrl',
                            id: 'id',
                            password: 'password',
                            role: 'role',
                            tableId: 'tableId',
                            username: 'username'
                        }]

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
                    ApprovalSup(row.id,state).then(response=> {
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
                    waitApprovalSup().then(response=>{
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
    