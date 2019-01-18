<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="用户名">
                    <el-input v-model="form.username" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-dropdown size="medium" split-button type="primary" @command="handleCommandPost">
                    {{ form.post }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in posts" :key="item.id">
                            <el-dropdown-item :command='{id:item.id,post:item.post}' >{{item.post}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
             <el-form-item label="状态">
                    <el-select v-model="form.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
              </el-form-item>
            <el-form-item>
                <el-dropdown size="medium" split-button type="primary" @command="handleCommandTeam">
                    {{ form.teamName }}
                    <el-dropdown-menu slot="dropdown">
                    <div v-for="item in teams" :key="item.id">
                            <el-dropdown-item :command='{id:item.id,teamName:item.teamName}' >{{item.teamName}}</el-dropdown-item>
                    </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-form-item>
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {addAdmin} from '@/api/role'
    import Form from "../../../../components/form/index";
    import {teamsGet,postGet} from '@/api/company'
    import store from '../../../../store'
    export default {
        name: "index",
        data(){
        return {
                  form: {
                    code: '1',
                    operator: '1',
                    password: '填写密码',
                    post: '选择岗位',
                    status: '启用',
                    team:'队伍',
                    username: '1',
                    teamName:'选择队伍',
                },
                posts:[{
                      'id': 0,
                      'post': 'post',
                      'permission':'permission'
                      }
                  ],
                teams:[{
                    'teamName':'teamName',
                    'addTime':'addTime',
                    'area':'area',
                    'id':'id',
                    'operator':'operator',
                    'status':'status',
                    'supervisor':'supervisor',
                    'verifyCode':'verifyCode'
                    }]
              }

        },

      components: {
        Form
      },
      created(){
        this.getData();
        this.form.operator = store.getters.uid;
      },
      methods:{
         handleCommandTeam(command) {
              // this.$message('click on item ' + command.id);
              this.form.team = command.id;
              this.form.teamName = command.teamName;
              // console.log('click on item ' + command.teamName);
          },
         handleCommandPost(command) {
              // this.$message('click on item ' + command.id);
              this.form.post = command.post;
              // this.form.teamName = command.teamName;
              // console.log('click on item ' + command.teamName);
          },
          getData(){
              // this.getcodes();
              this.getTeams();
              this.getPosts();
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
            getPosts(){
               postGet().then(response=>{
                    console.log(response,'response')
                      if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.posts = response.data;
                    }
                })
            },
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              addAdmin(this.form.code,this.form.operator,this.form.password,this.form.post,this.form.status,this.form.team,this.form.username).then(response => {
                // console.log(response.data.infoCode)
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
        },
      }
    }
    
</script>

<style scoped>

</style>
