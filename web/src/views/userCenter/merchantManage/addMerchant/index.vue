<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" style="width: 30%;" type="text" onkeyup="this.value=this.value.replace(/[^a-zA-Z]/g,'')" placeholder="不能使用中文"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" style="width: 30%;" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"></el-input>
      </el-form-item>
      <el-form-item label="等级">
        <!-- <el-input v-model="form.level" type="number" min="1"  style="width: 30%;"></el-input> -->
        <el-select v-model="form.level" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item label="状态">
                    <el-select v-model="form.status" placeholder="启用">
                    <el-option label="启用" value="启用"></el-option>
                    <el-option label="停用" value="停用"></el-option>
                    </el-select>
            </el-form-item>
      <el-form-item label="微信点位">
        <el-input v-model="form.wechat" style="width: 30%;" type="number"></el-input>
      </el-form-item>
      <el-form-item label="支付宝点位">
        <el-input v-model="form.alipay" style="width: 30%;" type="number"></el-input>
      </el-form-item>
      <!-- <el-form-item label="申请人id">
              <el-input v-model="form.applyId" style="width: 30%;"></el-input>
      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="onSubmit('form')">添加</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { addMerchant } from "@/api/role";
import Form from "../../../../components/form/index";
import store from "../../../../store";

export default {
  name: "index",
  data() {
    return {
      form: {
        alipay: 12,
        applyId: 1,
        level: 1,
        password: "",
        username: "",
        wechat: 12,
      },
        options: [
        {
          value: "1",
          label: "1"
        },
        {
          value: "2",
          label: "2"
        },
        {
          value: "3",
          label: "3"
        },
        {
          value: "4",
          label: "4"
        },
        {
          value: "5",
          label: "5"
        }
      ]
    };
  },

  components: {
    Form
  },
  created() {
    this.form.applyId = store.getters.uid;
  },
  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if (
            this.form.alipay.length == 0 ||
            this.form.applyId.length == 0 ||
            this.form.level.length == 0 ||
            this.form.password.length == 0 ||
            this.form.username == 0 ||
            this.form.wechat.length == 0
          ) {
            alert("請輸入完整信息");
            return;
          }

          // alert('submit!');
          addMerchant(
            this.form.alipay,
            this.form.applyId,
            this.form.level,
            this.form.password,
            this.form.username,
            this.form.wechat,
            this.form.status
          )
            .then(response => {
              // console.log(response.data.infoCode)
              if (response.data.infoCode) {
                this.$message({
                  message: response.data.description,
                  type: "warning"
                });
              } else {
                this.$message({
                  message: "添加成功",
                  type: "success"
                });
              }
              // const data = response.data
              // setToken(data.token)
              // commit('SET_TOKEN', data.token)
              resolve();
            })
            .catch(error => {
              // reject(error)
              this.$message(error);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    }
  }
};
</script>

<style scoped>
</style>
