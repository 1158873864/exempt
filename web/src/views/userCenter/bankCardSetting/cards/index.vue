<template>
        <div class="app-container">
        <div>所有银行卡</div>
            <el-table
            :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
            height="450"
            border
            style="width: 100%">
            <el-table-column prop="id" label="id" width="180"></el-table-column>
            <el-table-column prop="cardNumber" label="cardNumber" width="180"></el-table-column>
            <el-table-column prop="name" label="name" width="180"></el-table-column>
            <el-table-column prop="bank" label="bank" width="180"></el-table-column>
            <el-table-column prop="accountWithBank" label="accountWithBank" width="180"></el-table-column>
            <el-table-column prop="bin" label="bin" width="180"></el-table-column>
            <el-table-column prop="status" label="status" width="180"></el-table-column>
            <el-table-column prop="user.username" label="user" width="180"></el-table-column>
    
        </el-table>
        <div class="block" >
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
    import { cardsGet } from '@/api/personal'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        "id": 1,
                        "cardNumber": "string",
                        "name": "string",
                        "bank": "string",
                        "accountWithBank": "string",
                        "bin": "string",
                        "status": "string",
                        "user": {
                            "id": 2,
                            "username": "string",
                            "role": 3,
                            "tableId": 1,
                            "cards": []
                        }
                        }
                    ],
                    currentPage:1,
                    pagesize:1
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
                    cardsGet().then(response=>{
                        console.log(response,'sdll')
                         if(response.code!=200){
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