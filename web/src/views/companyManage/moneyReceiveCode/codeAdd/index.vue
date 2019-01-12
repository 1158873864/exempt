<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="codeAddParameters" class="demo-form-inline">
            <el-form-item label="duration">
                <el-input v-model="codeAddParameters.duration" placeholder="duration"></el-input>
            </el-form-item>
            <el-form-item label="info">
                <el-input v-model="codeAddParameters.info" placeholder="info"></el-input>
            </el-form-item>
            <el-form-item label="number">
                <el-input v-model="codeAddParameters.number" placeholder="number"></el-input>
            </el-form-item>
            <el-form-item label="priority">
                <el-input v-model="codeAddParameters.priority" placeholder="priority"></el-input>
            </el-form-item>
            <el-form-item label="team">
                <el-input v-model="codeAddParameters.team" placeholder="team"></el-input>
            </el-form-item>
            <el-form-item label="type">
                <el-input v-model="codeAddParameters.type" placeholder="type"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addcode">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { codeAdd,codesGet } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                codeAddParameters: {
                    "duration,": "duration,",
                    "info,": "info,",
                    "number,": "number,",
                    "priority,": "priority,",
                    "team,": "team,",
                    "type": "type"
                },
                codes:{},
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
                this.getcodes();
            },
            getcodes(){
                codesGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.codes = response.data;
                    }
                })
            },
            addcode() {
                codeAdd(
                    this.codeAddParameters.duration,
                    this.codeAddParameters.info,
                    this.codeAddParameters.number,
                    this.codeAddParameters.priority,
                    this.codeAddParameters.team,
                    this.codeAddParameters.type
                    ).then(response=>{
                    if(response.data.infocode){
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
                    this.getcodes();
                }
            }
        }
    }
</script>

<style scoped>

</style>
