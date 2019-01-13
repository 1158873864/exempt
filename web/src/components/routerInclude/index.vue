<template >
  <div style="padding:30px;">
    <el-alert :closable="false" :title="manageTitle">
      <router-view />
    </el-alert>
  </div>
</template>

<script>
export default {
  data() {
    return {
     props: {
        manageTitle: {
            type: String,
            default: '后台管理系统'
            }
        }
    }
  }
}
</script>