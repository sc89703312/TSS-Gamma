<template>
  <div>
    <h4 class="form-title">新建课程</h4>
    <el-form ref="form" :model="form" label-width="80px" size="medium" label-position="left">
      <el-form-item label="课程名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="onSubmit">立即创建</el-button>
        <el-button size="small" @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    name: 'CreateCourse',
    data () {
      return {
        form: {
          name: ''
        }
      }
    },
    methods: {
      onSubmit () {
      },
      onCancel () {
        this.$router.back()
      }
    }
  }
</script>
