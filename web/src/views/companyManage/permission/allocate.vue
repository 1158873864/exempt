<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="permissionaddParameters" class="demo-form-inline">
            <el-form-item label="post">
                <el-input v-model="permissionaddParameters.post" placeholder="post"></el-input>
            </el-form-item>
            <el-form-item label="permission">
            </el-form-item>
                <el-tree
                    :props="props"
                    :data="treepermissions"
                    show-checkbox
                    node-key="title"
                    ref="tree"
                    show-checkbox
                    @check-change="handleCheckChange">
                </el-tree>
            <el-form-item>
                <el-button type="primary" @click="addpermission">添加</el-button>
            </el-form-item>
        </el-form>
        


  </div>
</template>

<script>
import { permissionAllocate } from '@/api/company'
import { getTreePermissions } from '@/api/permissions'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                permissionaddParameters: {
                        "post": "post",
                        "permission": "permission",
                },
                currentPage:1,
                treepermissions:[],
                props: {
                    children: 'children',
                    label: 'title'
                },
            }
        },
        created(){
            this.treepermissions = getTreePermissions();
            console.log(this.treepermissions)
        },
        methods: {
            addpermission() {
                this.getCheckedKeys();
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
            getCheckedKeys(){
                this.permissionaddParameters.permission = this.$refs.tree.getCheckedKeys();
                console.log(this.permissionaddParameters.permission)
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
