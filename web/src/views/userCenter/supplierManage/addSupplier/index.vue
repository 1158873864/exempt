<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="等级">
                    <el-input v-model="form.level" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="form.password" style="width: 30%;"></el-input>
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
    import {addSupplier} from '@/api/role'
    import Form from "../../../../components/form/index"
    import store from '../../../../store'

    export default {
        name: "index",
        data(){
        return {
                  form: {
                    id: '1',
                    level: 1,
                    password: '1',
                    username: '1'
                  },
              }

        },

      components: {
        Form
      },
      created(){
        this.form.id = store.getters.uid;
      },
      methods:{
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              addSupplier(this.form.id,this.form.level,this.form.password,this.form.username).then(response => {
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
