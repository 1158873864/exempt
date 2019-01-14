<template>
    <div class="app-container">
    <div>团队管理</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        max-height="500"
        ref="table"
       >
        <el-table-column prop="teamName" label="teamName" width="100" align="center"></el-table-column>
        <el-table-column prop="addTime" label="addTime" width="180" align="center"></el-table-column>
        <el-table-column prop="area" label="area" width="180" align="center"></el-table-column>
        <el-table-column prop="operator" label="operator" width="100" align="center"></el-table-column>
        <el-table-column prop="status" label="status" width="180" align="center"></el-table-column>
        <el-table-column prop="supervisor" label="supervisor" width="100" align="center"></el-table-column>
        <el-table-column prop="verifyCode" label="verifyCode" width="100" align="center"></el-table-column>
        <el-table-column label="actions" align="center" width="230" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">edit</el-button>
              
            </template>
          </el-table-column>
    </el-table>
    <div class="block">
        <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pagesize"
        layout="sizes, prev, pager, next"
        :total="1000">
        </el-pagination>
    </div>

    <el-dialog title="Edit" :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="70px" style="width: 400px; margin-left:50px;">
          
          <el-form-item label="title" prop="title">
            <el-input v-model="teams.id"/>
          </el-form-item>
         
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="updateData()">确认</el-button>
        </div>
      </el-dialog>

  </div>
</template>

<script>
import { teamAdd,teamsGet } from '@/api/team'
    export default {
        data() {
            return {
                tableHeight: 290,
                activeNames: ['1'],
                labelPosition: 'right',
                teamAddParameters: {
                        "area": "area",
                        "operator": "operator",
                        "status": "status",
                        "supervisor": "supervisor",
                        "teamName": "teamName",
                        "verifyCode": "verifyCode"
                },
                teams:[{
                    'teamName':'teamName',
                    'addTime':'addTime',
                    'area':'area',
                    'id':'id',
                    'operator':'operator',
                    'status':'status',
                    'supervisor':'supervisor',
                    'verifyCode':'verifyCode'
                    }
                ],
                currentPage:1,
                pagesize:10,
                dialogFormVisible:false
            }
        },
        created(){
            this.getData();
        },
        methods: {
            handleUpdate(row) {
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pagesize = val;
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
            },
            getData(){
                this.getTeams();
            },
            getTeams(){
                teamsGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.teams = response.data;
                    }
                })
            },
            handleChange(val) {
                console.log(val);
                  if(val==2)
                {
                    this.getTeams();
                }
            }
        }
    }
</script>

<style scoped>

</style>
