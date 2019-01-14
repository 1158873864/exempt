<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
      
            <el-form-item label="当日佣金">
              <el-input v-model="form.brokerage" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="form.code" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="流量">
              <el-input v-model="form.flow" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="状态">
              <el-input v-model="form.status" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="用户名">
                    <el-input v-model="form.username" style="width: 30%;"></el-input>
            </el-form-item>
        
      
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {addAgent} from '@/api/role'
    import Form from "../../../../components/form/index";

    export default {
        name: "index",
        data(){
        return {
                  form: {
                    brokerage: '1',
                    code: '1',
                    flow: '1',
                    password: '1',
                    status: '1',
                    username: '1'
                },
                
              }

        },

      components: {
        Form
      },
      methods:{
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              addAgent(this.form.brokerage,this.form.code,this.form.flow,this.form.password,this.form.status,this.form.username).then(response => {
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
