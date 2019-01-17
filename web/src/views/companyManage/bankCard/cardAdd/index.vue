<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="cardAddParameters" class="demo-form-inline">
            <el-form-item label="归属">
                <el-input v-model="cardAddParameters.attribution" placeholder="归属" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="余额">
                <el-input type="number" v-model="cardAddParameters.balance" placeholder="0" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="银行">
                <el-input v-model="cardAddParameters.bank" placeholder="银行" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="所属人">
                <el-input v-model="cardAddParameters.name" placeholder="所属人" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="卡号">
                <el-input v-model="cardAddParameters.number" placeholder="卡号" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="关联">
                <el-input v-model="cardAddParameters.relation" placeholder="关联" style="width: 30%;"></el-input>
            </el-form-item>
             <el-form-item label="状态">
                    <el-select v-model="cardAddParameters.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
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
                        "attribution": "",
                        "balance": 0,
                        "bank": "",
                        "name": "",
                        "number": "",
                        "relation": "",
                        "status": "",
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
