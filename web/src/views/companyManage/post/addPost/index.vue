<template>
    <div class="app-container">
        <div>添加岗位</div>
        <el-form :label-position="labelPosition" :model="postaddParameters" class="demo-form-inline">
            <el-form-item label="职位">
                <el-input v-model="postaddParameters.post" placeholder="post" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="postAdd">添加</el-button>
            </el-form-item>
        </el-form>
  </div>
</template>

<script>
import { addPost } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                postaddParameters: {
                        "post": "post",
                }
            }
        },
        methods: {
            postAdd(){
                    addPost(this.postaddParameters.post,).then(response=>{
                        console.log(response,'sdll')
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
                            // response.data.forEach(el => {
                            //     el.permission = el.permission.join(',')
                            // });
                            // this.teams = response.data;
                        }
                    })
                },
            // addpost() {
            //     postAdd(
            //         this.postaddParameters.post,
            //         // this.postaddParameters.post,
            //         ).then(response=>{
            //         if(response.data.infoCode){
            //             this.$message({
            //                 message: response.data.description,
            //                 type: 'warning'
            //             });
            //         }else{
            //             this.$message({
            //                 message: '添加成功',
            //                 type: 'success'
            //             });
            //         }
            //     })
            // }
        }
    }
</script>

<style scoped>

</style>
