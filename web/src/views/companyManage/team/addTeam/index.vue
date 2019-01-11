<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-collapse v-model="activeNames" @change="handleChange">
        <el-collapse-item title="添加团队" name="1">
           <el-form :label-position="labelPosition" :model="addteamFrom" class="demo-form-inline">
                <el-form-item label="area">
                    <el-input v-model="addteamFrom.area" placeholder="area"></el-input>
                </el-form-item>
                <el-form-item label="operator">
                    <el-input v-model="addteamFrom.operator" placeholder="operator"></el-input>
                </el-form-item>
                <el-form-item label="status">
                    <el-input v-model="addteamFrom.status" placeholder="status"></el-input>
                </el-form-item>
                <el-form-item label="supervisor">
                    <el-input v-model="addteamFrom.supervisor" placeholder="supervisor"></el-input>
                </el-form-item>
                <el-form-item label="teamName">
                    <el-input v-model="addteamFrom.teamName" placeholder="teamName"></el-input>
                </el-form-item>
                <el-form-item label="verifyCode">
                    <el-input v-model="addteamFrom.verifyCode" placeholder="verifyCode"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addteam">添加</el-button>
                </el-form-item>
            </el-form>
        </el-collapse-item>
        </el-collapse>
        
  </div>
</template>

<script>
import { teamAdd } from '@/api/team'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                addteamFrom: {
                        "area": "area",
                        "operator": "operator",
                        "status": "status",
                        "supervisor": "supervisor",
                        "teamName": "teamName",
                        "verifyCode": "verifyCode"

                }
            }
        },
        methods: {
            addteam() {
                teamAdd(this.addteamFrom).then(response=>{
                    if(response.data.infoCod){
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
            }
        }
    }
</script>

<style scoped>

</style>
