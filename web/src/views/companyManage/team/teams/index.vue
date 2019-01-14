<template>
    <div class="app-container">
    <div>团队管理</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        max-height="500"
        ref="table"
       >
        <el-table-column prop="teamName" label="teamName" width="180"></el-table-column>
        <el-table-column prop="addTime" label="addTime" width="180"></el-table-column>
        <el-table-column prop="area" label="area" width="180"></el-table-column>
        <el-table-column prop="id" label="id" width="180"></el-table-column>
        <el-table-column prop="operator" label="operator" width="180"></el-table-column>
        <el-table-column prop="status" label="status" width="180"></el-table-column>
        <el-table-column prop="supervisor" label="supervisor" width="180"></el-table-column>
        <!-- <el-table-column prop="verifyCode" label="verifyCode" width="180"></el-table-column> -->
        <el-table-column label="操作" fixed="right" width="180">
            <template scope="scope">
                <el-button size="small"
                        @click="openDialog(scope.$index,scope.row)">修改</el-button>
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

    <el-dialog title="修改团队信息" :visible.sync="dialogFormVisible">
        <el-form :model="newRow">
            <el-form-item label="new_area">
                <el-input v-model="newRow.area" placeholder="area"></el-input>
            </el-form-item>
            <el-form-item label="new_operator">
                <el-input v-model="newRow.operator" placeholder="operator"></el-input>
            </el-form-item>
            <el-form-item label="new_status">
                <el-input v-model="newRow.status" placeholder="status"></el-input>
            </el-form-item>
            <el-form-item label="new_teamName">
                <el-input v-model="newRow.teamName" placeholder="teamName"></el-input>
            </el-form-item>
            <el-form-item label="new_verifyCode">
                <el-input v-model="newRow.verifyCode" placeholder="verifyCode"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="updateTeam">确 定</el-button>
        </div>
    </el-dialog>

  </div>
  
</template>

<script>
import { teamAdd,teamsGet,teamDelete,teamVerifyCodeCheck,teamUpdate } from '@/api/company'
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
                dialogFormVisible: false,
                newRowIndex:1,
                newRow: {
                    area: '',
                    operator: '',
                    status: '',
                    supervisor: '',
                    teamName: '',
                    verifyCode: ''
                    },
                formLabelWidth: '120px'
            }
        },
        created(){
            this.getData();
        },
        methods: {
            openDialog(index,row){
                var verifyCode = '';
                var flag = false;
                this.$prompt('请输入团队验证码', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                    }).then(({ value }) => {
                        verifyCode = value;
                        this.checkVerify(index,row,verifyCode)
                    }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            checkVerify(index, row,verifyCode) {
                console.log(row);
                var flag = false;
                teamVerifyCodeCheck(row.id,verifyCode).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        flag=true;
                        this.newRow = JSON.parse(JSON.stringify(row));;
                        this.newRowIndex = index;
                        this.dialogFormVisible = true;
                    }
                });
                return flag;
            },
            updateTeam(){
                teamUpdate(this.newRow.area,this.newRow.operator,this.newRow.status,this.newRow.supervisor,this.newRow.teamName,this.newRow.verifyCode,this.newRow.id).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.teams[this.newRowIndex] = this.newRow;
                        this.dialogFormVisible = false;
                         this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                });
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
