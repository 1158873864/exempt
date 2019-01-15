<template>
    <div class="app-container">
    <div>所有供码用户</div>
        <el-table
        :data="teams.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        height="450"
        border
        style="width: 100%">
        <el-table-column prop="user.username" label="用户名"  align="center"></el-table-column>
        <el-table-column prop="priority" label="等级"  align="center"></el-table-column>
        <el-table-column prop="devices_team" label="设备/状态"  align="center"></el-table-column>
        <el-table-column prop="status" label="状态"  align="center"></el-table-column>
        <el-table-column label="操作" fixed="right" align="center" >
                <template scope="scope" >
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
    <el-dialog title="修改供码用户信息" :visible.sync="dialogFormVisible">
            <el-form :model="newRow">
                <el-form-item label="username">
                    <el-input v-model="newRow.user.username" placeholder="area"></el-input>
                </el-form-item>
                <el-form-item label="codeType">
                    <el-input v-model="newRow.codeType" placeholder="codeType"></el-input>
                </el-form-item>
                <el-form-item label="level">
                    <el-input v-model="newRow.level" placeholder="level"></el-input>
                </el-form-item>
                <el-form-item label="password">
                    <el-input v-model="newRow.password" placeholder="password"></el-input>
                </el-form-item>
               
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateSupplier">确 定</el-button>
            </div>
    </el-dialog>
  </div>
</template>

<script>
import { suppliersGet,supplierUpdate } from '@/api/role'
    export default {
        data() {
            return {
                teams:[{
                    "priority": 0,
                    "devices": [],
                    "status": "",
                    "user": {},
                    "devices_team":" ",
                    }
                ],
                
                newRow: {
                    "codeType": "",
                    "level": 0,
                    "password": "",
                    "user": {}
                    },
                currentPage:1,
                pagesize:10,
                newRowIndex:1,
                dialogFormVisible: false
            }
        },
        created(){
            this.getData();
        },
        methods: {
            updateSupplier() {
                supplierUpdate(this.newRow.codeType,this.newRow.level,this.newRow.password,this.newRow.id).then(response=> {
                    if(response.code!=200){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        this.teams[this.newRowIndex].priority = this.newRow.level;
                        this.dialogFormVisible = false;
                         this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                    }
                }); 
            },
            openDialog(index,row) {
                this.dialogFormVisible=true;
                //this.newRow = JSON.parse(JSON.stringify(row));
                this.newRow = this.teams[index];
                this.newRow.password=this.teams[index].user.password;
                this.newRow.level=this.teams[index].priority;
                console.log(this.newRow);
                this.newRowIndex = index;
                console.log(index);

            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
                this.pagesize=val;
              
            },
            handleCurrentChange(val) {
                console.log(`当前页: ${val}`);
                this.currentPage=val;
            },
            getData(){
                this.getTeams();
            },
            getTeams(){
                suppliersGet().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                        
                        
                       this.teams = response.data;
                       var i=0;
                       var j=0;
                       console.log(this.teams[2].devices.length);
                       console.log(this.teams.length)
                       for(j=0;j<this.teams.length;j++)
                       {
                           this.teams[j].devices_team=" ";
                           for(i=0;i<this.teams[j].devices.length;i++)
                           {
                              if(this.teams[j].devices[i].online==1)
                              {
                                this.teams[j].devices_team=this.teams[j].devices_team+this.teams[j].devices[i].imei+'/在线,';
                              }
                              else{
                                this.teams[j].devices_team=this.teams[j].devices_team+this.teams[j].devices[i].imei+'/离线,';

                              }
                           }
                           console.log(this.teams[j].devices_team,'xxcc');
                       }
                       
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
