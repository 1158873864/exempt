<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
      
            <el-form-item label="brokerage">
              <el-input v-model="form.brokerage"></el-input>
            </el-form-item>
            <el-form-item label="code">
              <el-input v-model="form.code"></el-input>
            </el-form-item>
            <el-form-item label="flow">
              <el-input v-model="form.flow"></el-input>
            </el-form-item>
            <el-form-item label="password">
              <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="status">
              <el-input v-model="form.status"></el-input>
            </el-form-item>
            <el-form-item label="username">
                    <el-input v-model="form.username"></el-input>
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
