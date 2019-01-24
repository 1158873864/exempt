<template>
  <!-- <div>团队管理</div> -->
    <div class="app-container">
        <el-form :label-position="labelPosition" :model="permissionaddParameters" class="demo-form-inline">
            <el-form-item label="职位">
             <el-select  style="width: 200px" v-model="permissionaddParameters.post">
                 <el-option
                    v-for="item in posts"
                    :key="item.id"
                    :label="item.post"
                    :value="item.post">
                    </el-option>
            </el-select >
            </el-form-item>
            <el-form-item label="权限">
            </el-form-item>
                <el-tree
                    :props="props"
                    :data="treepermissions"
                    show-checkbox
                    node-key="title"
                    ref="tree"
                   
                   >
                </el-tree>
            <el-form-item>
                <el-button type="primary" @click="addpermission">添加</el-button>
            </el-form-item>
        </el-form>
        


  </div>
</template>

<script>
import { permissionAllocate,postGet } from '@/api/company'
import { getTreePermissions } from '@/api/permissions'
import {getIds} from '@/utils/treeids'

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
                posts:[{}]
            }
        },
        created(){
            this.treepermissions = getTreePermissions();
            console.log(this.treepermissions)
            this.getPost();
        },
        methods: {
            getPost(){
                 postGet().then(response=>{
                        console.log(response,'response')
                         if(response.code!=200){
                            this.$message({
                                message: response.data.description,
                                type: 'warning'
                            });
                        }else{
                            this.posts = response.data;
                        }
                    })
            },
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
                this.permissionaddParameters.permission = this.permissionaddParameters.permission.concat(this.$refs.tree.getHalfCheckedKeys());
                // console.log(this.permissionaddParameters.permission,this.permissionaddParameters.permission1 )
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
