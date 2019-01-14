<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px"> 
            <el-form-item label="alipayloginID">
              <el-input v-model="form.alipayloginID"></el-input>
            </el-form-item>
            <el-form-item label="level">
                    <el-input v-model="form.level"></el-input>
            </el-form-item>
            <el-form-item label="password">
              <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="username">
              <el-input v-model="form.username"></el-input>    
            </el-form-item>
              <el-button type="primary" @click="onSubmit('form')">添加</el-button>
              <el-button>取消</el-button>
            </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import {addSupplier} from '@/api/role'
    import Form from "../../../../components/form/index";

    export default {
        name: "index",
        data(){
        return {
                  form: {
                    alipayloginID: '1',
                    level: 0,
                    password: '1',
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
              addSupplier(this.form.alipayloginID,this.form.level,this.form.password,this.form.username).then(response => {
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
