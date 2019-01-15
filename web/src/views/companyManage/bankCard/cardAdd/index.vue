<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="cardAddParameters" class="demo-form-inline">
            <el-form-item label="归属">
                <el-input v-model="cardAddParameters.attribution" placeholder="attribution" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="余额">
                <el-input type="number" v-model="cardAddParameters.balance" placeholder="balance" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="银行">
                <el-input v-model="cardAddParameters.bank" placeholder="bank" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="所属人">
                <el-input v-model="cardAddParameters.name" placeholder="name" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="卡号">
                <el-input v-model="cardAddParameters.number" placeholder="number" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="关联">
                <el-input v-model="cardAddParameters.relation" placeholder="relation" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-input v-model="cardAddParameters.status" placeholder="status" style="width: 30%;"></el-input>
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
