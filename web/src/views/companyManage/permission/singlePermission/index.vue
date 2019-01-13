<template>
        <div class="app-container">
        <div>职位权限查询</div>
        <el-form ref="form" :model="form" label-width="80px">
      
                <el-form-item label="post">
                  <el-input v-model="form.post"></el-input>
          <el-button type="primary" @click="onSubmit('form')">查询</el-button>
          <el-button>取消</el-button>
        
        </el-form-item>
        </el-form>
     
            <el-table
            :data="teams"
            height="250"
            border
            style="width: 100%">
            <el-table-column prop="post" label="post" width="180"></el-table-column>
            <el-table-column prop="permission" label="permission" width="180"></el-table-column>

            
    
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
    import { checkSinglePermission } from '@/api/company'
    import Form from "../../../../components/form/index";

        export default {
            data() {
                return {
                    form: {
                        post:'admin'
                    },
                    activeNames: ['1'],
                    labelPosition: 'right',
                    
                    teams:[{
                        //post: 'this.form.post',
                        permission:'permission'
                        
                        }
                    ],
                    currentPage:1
                }
            },
            components: {
        Form
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
                    checkSinglePermission(this.form.post).then(response=>{
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
                },
                onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              checkSinglePermission(this.form.post).then(response => {
                // console.log(response.data.infoCode)
                if(response.data.infoCode){
                   this.$message({
                    message: response.data.description,
                    type: 'warning'
                  });
                }else{
                  this.$message({
                    message: '查询成功',
                    type: 'success'
                  });
                  this.teams = response.data;
                }
                // const data = response.data
                // setToken(data.token)
                // commit('SET_TOKEN', data.token)
                resolve()
              }).catch(error => {
                // reject(error)
                 this.$message(error);
              })
            } else {
              console.log('error submit!!');
              return false;
            }
          });
        }
            }
        }
    </script>
    
    <style scoped>
    
    </style>
    