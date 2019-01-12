<template>
    <div class="app-container">
    <div>团队管理</div>
        <el-table
        :data="codes"
        height="250"
        border
        style="width: 100%">
        <el-table-column prop="accountInfo" label="accountInfo" width="180"></el-table-column>
        <el-table-column prop="accountNumber" label="accountNumber" width="180"></el-table-column>
        <el-table-column prop="duration" label="duration" width="180"></el-table-column>
        <el-table-column prop="id" label="id" width="180"></el-table-column>
        <el-table-column prop="priorityLevel" label="priorityLevel" width="180"></el-table-column>
        <el-table-column prop="codeName" label="codeName" width="180"></el-table-column>
        <el-table-column prop="type" label="type" width="180"></el-table-column>

    </el-table>
    <div class="block">
        <span class="demonstration">调整每页显示条数</span>
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="100"
        layout="sizes, prev, pager, next"
        :total="1000">
        </el-pagination>
    </div>
  </div>
</template>

<script>
import { codesGet } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                codes:[{
                    'accountInfo':'accountInfo',
                    'accountNumber':'accountNumber',
                    'duration':'duration',
                    'id':'id',
                    'priorityLevel':'priorityLevel',
                    'codeName':'codeName',
                    'type':'type',
                    }
                ],
                currentPage:1
            }
        },
        created(){
            this.getData();
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
                       this.codes = response.data.receiptCodeList;
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
