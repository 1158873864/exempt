<template>
  <div class="dashboard-container">
    <el-card class="dashboard-card">
      <div slot="header">当日总量</div>
      <div class="dashboard-card-content">
        10000.0
      </div>
    </el-card>
    <el-card class="dashboard-card">
      <div slot="header">可用余额</div>
      <div class="dashboard-card-content">
        10000.0
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    display: flex;
  }
  &-card {
    width: 50%;
    height: 200px;
    margin-right: 20px;
    &-content {
      text-align: center;
      font-size: 40px;
      margin-top: 30px;
    }
  }
}
</style>
