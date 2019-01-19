<template>
        <div class="app-container">
          <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="银行账户">
              <el-input v-model="form.accountOfBank"></el-input>
            </el-form-item>
            <el-form-item label="银行">
              <el-input v-model="form.bank"></el-input>
            </el-form-item>
            <el-form-item label="bin">
              <el-input v-model="form.bin"></el-input>
            </el-form-item>
            <el-form-item label="名字">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="数字">
                    <el-input v-model="form.number"></el-input>
            </el-form-item>
            <el-form-item label="状态">
                    <el-select v-model="form.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
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
              accountOfBank: '',
              bank: '',
              bin: '',
              name: '',
              number: '',
              status:'',
              uid: 0
          },
          
        }

        },
      created(){
          this.form.uid = store.getters.uid;
          console.log(store.getters.uid);
      },
      methods:{
        onSubmit(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              // alert('submit!');
              console.log(this.form.uid);
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
