<template>
    <div class="app-container">
        <div>添加岗位</div>
        <el-form :label-position="labelPosition" :model="permissionaddParameters" class="demo-form-inline">
            <el-form-item label="post">
                <el-input v-model="permissionaddParameters.post" placeholder="post"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="addpermission">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { permissionAllocate } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                permissionaddParameters: {
                        "post": "post",
                        "permission": "permission",
                },
                currentPage:1
            }
        },
        methods: {
            addpermission() {
                permissionAllocate(
                    this.permissionaddParameters.post,
                    this.permissionaddParameters.permission,
                    ).then(response=>{
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
                })

            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getpermissions();
                }
            }
        }
    }
</script>

<style scoped>

</style>
