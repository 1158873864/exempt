<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="80px">

      <el-form-item label="付款码">
        <el-input v-model="form.code"></el-input>
      </el-form-item>
      <el-form-item label="操作">
        <el-input v-model="form.operator"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password"></el-input>
      </el-form-item>
      <el-form-item label="职位">
        <el-input v-model="form.post"></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-input v-model="form.status"></el-input>
      </el-form-item>
      <el-form-item label="团队">
        <el-input v-model="form.team"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
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
import { addAdmin } from '@/api/role'
import Form from "../../../components/form/index";

    export default {
      name: "index",
      data(){
        return {
                  form: {
                  code: '1',
                  operator: '1',
                  password: '1',
                  post: '1',
                  status: '1',
                  team: '1',
                  username: '1'
                  // time: '',
                },
                //  loginRules: {
                //   username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                //   password: [{ required: true, trigger: 'blur', validator: validatePass }]
                // },
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
