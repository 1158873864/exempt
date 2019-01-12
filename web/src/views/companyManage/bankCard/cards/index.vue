<template>
    <div class="app-container">
    <div>团队管理</div>
        <el-table
        :data="cards"
        height="250"
        border
        style="width: 100%">

        <el-table-column prop="attribution" label="attribution" width="180"></el-table-column>
        <el-table-column prop="balance" label="balance" width="180"></el-table-column>
        <el-table-column prop="bank" label="bank" width="180"></el-table-column>
        <el-table-column prop="cardNumber" label="cardNumber" width="180"></el-table-column>
        <el-table-column prop="id" label="id" width="180"></el-table-column>
        <el-table-column prop="name" label="name" width="180"></el-table-column>
        <el-table-column prop="relation" label="relation" width="180"></el-table-column>
        <el-table-column prop="status" label="status" width="180"></el-table-column>

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
import { cardsGet } from '@/api/company'
    export default {
        data() {
            return {
                activeNames: ['1'],
                labelPosition: 'right',
                cards:[{
                    'attribution':'attribution',
                    'balance':'balance',
                    'bank':'bank',
                    'cardNumber':'cardNumber',
                    'id':'id',
                    'name':'name',
                    'relation':'relation',
                    'status':'status',
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
                       this.cards = response.data.companyCardList;
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
