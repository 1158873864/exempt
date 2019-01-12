<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">      
            <el-form-item label="alipay">
              <el-input v-model="form.alipay"></el-input>
            </el-form-item>
            <el-form-item label="balance">
              <el-input v-model="form.balance"></el-input>
            </el-form-item>
            <el-form-item label="code">
              <el-input v-model="form.code"></el-input>
            </el-form-item>
            <el-form-item label="password">
              <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="superior">
              <el-input v-model="form.superior"></el-input>
            </el-form-item>
            <el-form-item label="username">
                    <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="wechat">
                    <el-input v-model="form.wechat"></el-input>
            </el-form-item>
        
      
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {addMerchant} from '@/api/role'
    import Form from "../../../../components/form/index";

    export default {
        name: "index",
        data(){
        return {
                  form: {
                    alipay: '1',
                    balance: '1',
                    code: '1',
                    password: '1',
                    superior: '1',
                    username: '1',
                    wechat:'1'
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
              addMerchant(this.form.alipay,this.form.balance,this.form.code,this.form.password,this.form.superior,this.form.username,this.form.wechat).then(response => {
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
