<template>
<div>
  <div>获取QrCode</div>
        <el-form ref="form" :model="formparameters" label-width="100px">      
            <el-form-item label="金额">
                <el-input v-model="formparameters.money" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="formparameters.memo" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item label="ip">
              <el-input v-model="formparameters.ip" style="width: 30%;"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getCode">获取二维码</el-button>
                <!-- <el-button>取消</el-button> -->
            </el-form-item>
                <el-form-item>
                    <img :src="img_src" />
                </el-form-item>
      </el-form>
    <el-form>
        
    </el-form>
</div>
</template>

<script>
import store from '../../../store'
import {qrCodeGet,redirect} from '@/api/personal'
// import BASE_API from '../../../../config/prod.env'

    export default {
         data() {
                return {
                   qrCode:'',
                   formparameters:{
                    id:'123',
                    ip:'192.168.1.1',
                    memo:'1234123',
                    merchantId:'9',
                    money: '0.01',
                    sign:'1234132',
                    time:'time',
                    type:''
                   },
                   urlBase:"http://qr.liantu.com/api.php?text=",
                   showqrcodeurl:'alipays://platformapi/startapp?appId=20000123%26actionType=scan%26biz_data={"s": "money","u":"2088022126490523","a":"0.1","m":"11547555613680009"}',
                   img_src:''
                }
            },
            created(){
                // this.getData();
                this.formparameters.merchantId = store.getters.uid
                this.formparameters.time = Date.parse(new Date())/1000;
                // this.img_src = this.urlBase+this.showqrcodeurl;
                // console.log(this.img_src)
                // const prodEnv = require('../../../../config/prod.env')
                this.BASE_API = 'https://junrongcenter.com:8080';
                this.img_src = this.urlBase+this.BASE_API;
            },
            methods:{
                getCode(){
                     this.formparameters.time = Date.parse(new Date())/1000;
                    qrCodeGet(this.formparameters.id, this.formparameters.ip, this.formparameters.memo, this.formparameters.merchantId, this.formparameters.money, this.formparameters.sign, this.formparameters.time ).then(res=>{
                        console.log(res)
                         if(res.code!=200){
                            this.$message({
                                message: res.data.reason,
                                type: 'warning'
                                });
                        }else{
                            this.img_src = this.urlBase + this.BASE_API + res.data.url +"?orderId="+res.data.orderId
                            console.log(this.img_src)
                        }
                    })
                },
            }
    }
</script>

<style scoped>

</style>
