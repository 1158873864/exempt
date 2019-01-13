<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="accountOfBank">
              <el-input v-model="form.accountOfBank"></el-input>
            </el-form-item>
            <el-form-item label="bank">
              <el-input v-model="form.bank"></el-input>
            </el-form-item>
            <el-form-item label="bin">
              <el-input v-model="form.bin"></el-input>
            </el-form-item>
            <el-form-item label="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="number">
                    <el-input v-model="form.number"></el-input>
            </el-form-item>
            <el-form-item label="status">
                    <el-input v-model="form.status"></el-input>
            </el-form-item>
          <el-form-item>
          <el-button type="primary" @click="onSubmit('form')">添加</el-button>
          <el-button>取消</el-button>
        </el-form-item>
      </el-form>
        </div>
      </template>

<script>
    import { cardAdd } from '@/api/personal'
    import store from '../../../../store'
    export default {
        name: "index",
        data(){
        return {
            form: {
              accountOfBank: '1',
              bank: '1',
              bin: '1',
              name: '1',
              number: '1',
              status:'1',
              uid: 0
          },
          
        }

        },
      created(){
          this.form.uid = store.getters.uid;
      },
      methods:{
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              cardAdd(this.form.accountOfBank,this.form.bank,this.form.bin,this.form.name,this.form.number,this.form.status,this.form.uid).then(response => {
                // console.log(response.data.infoCode)
                if(response.code!=200){
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
