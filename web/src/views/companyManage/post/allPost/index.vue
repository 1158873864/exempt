<template>
        <div class="app-container">
        <div>所有岗位</div>
            <el-table
            :data="teams"
            max-height="700"

            >
            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" label="权限" inline class="demo-table-expand">
                        <el-form-item  v-for="(item, index) in props.row.permission" :key="index">
                            <span>{{ item }}</span>
                        </el-form-item>

                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="post" label="post" width="180"></el-table-column>
            <!-- <el-table-column prop="permission" label="permission" width="180"></el-table-column> -->
        </el-table>
        <div class="block" v-if="teams.length>10">
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
    import { checkAllPermission } from '@/api/company'
        export default {
            data() {
                return {
                    activeNames: ['1'],
                    labelPosition: 'right',
                    teams:[{
                        'post': 'post',
                        // 'permission':'permission'
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
                    checkAllPermission().then(response=>{
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
    