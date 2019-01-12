<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="cardAddParameters" class="demo-form-inline">
            <el-form-item label="accountInfo">
                <el-input v-model="cardAddParameters.attribution" placeholder="attribution"></el-input>
            </el-form-item>
            <el-form-item label="accountNumber">
                <el-input v-model="cardAddParameters.balance" placeholder="balance"></el-input>
            </el-form-item>
            <el-form-item label="duration">
                <el-input v-model="cardAddParameters.bank" placeholder="bank"></el-input>
            </el-form-item>
            <el-form-item label="id">
                <el-input v-model="cardAddParameters.name" placeholder="name"></el-input>
            </el-form-item>
            <el-form-item label="priorityLevel">
                <el-input v-model="cardAddParameters.number" placeholder="number"></el-input>
            </el-form-item>
            <el-form-item label="cardName">
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
                        "balance": "balance",
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
            getcards(){
                cardsGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.cards = response.data;
                    }
                })
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
                    if(response.data.infocard){
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
