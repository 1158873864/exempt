<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="cardAddParameters" class="demo-form-inline">
            <el-form-item label="attribution">
                <el-input v-model="cardAddParameters.attribution" placeholder="attribution"></el-input>
            </el-form-item>
            <el-form-item label="balance">
                <el-input type="number" v-model="cardAddParameters.balance" placeholder="balance"></el-input>
            </el-form-item>
            <el-form-item label="bank">
                <el-input v-model="cardAddParameters.bank" placeholder="bank"></el-input>
            </el-form-item>
            <el-form-item label="name">
                <el-input v-model="cardAddParameters.name" placeholder="name"></el-input>
            </el-form-item>
            <el-form-item label="number">
                <el-input v-model="cardAddParameters.number" placeholder="number"></el-input>
            </el-form-item>
            <el-form-item label="relation">
                <el-input v-model="cardAddParameters.relation" placeholder="relation"></el-input>
            </el-form-item>
            <el-form-item label="type">
                <el-input v-model="cardAddParameters.status" placeholder="status"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addcard">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { cardAdd } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                cardAddParameters: {
                        "attribution": "attribution",
                        "balance": 8654654654,
                        "bank": "bank",
                        "name": "name",
                        "number": "number",
                        "relation": "relation",
                        "status": "status"
                },
                cards:{},
                currentPage:1
            }
        },
        methods: {
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                this.getcards();
            },
            addcard() {
                cardAdd(
                this.cardAddParameters.attribution,
                this.cardAddParameters.balance,
                this.cardAddParameters.bank,
                this.cardAddParameters.name,
                this.cardAddParameters.number,
                this.cardAddParameters.relation,
                this.cardAddParameters.status
                ).then(response=>{
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
                })

            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getcards();
                }
            }
        }
    }
</script>

<style scoped>

</style>
