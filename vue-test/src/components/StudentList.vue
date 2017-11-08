<template>
  <div>
    <el-row :gutter="80" type="flex" align="middle">
      <el-col :span="2">
        <h4 class="form-title">考卷生成</h4>
      </el-col>
      <el-col :span="20">
        <span class="form-subtitle">选择考生，生成试卷</span>
      </el-col>
    </el-row>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item label="学生列表" class="last-form-item">
        <el-checkbox :indeterminate="form.isIndeterminate" v-model="form.checkAll" @change="handleCheckAllChange">全选</el-checkbox>
        <el-checkbox-group v-model="form.checkedStudents" @change="handleCheckedStudentsChange">
          <el-checkbox style="margin-left: 0px; margin-right: 30px" v-for="student in form.students" :label="student" :key="student.id">{{student.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="small" @click="onSubmit">试卷生成</el-button>
        <el-button @click="onCancel" size="small">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style>
  .el-form {
    width: 480px;
  }

  .form-title {
    width: 68px;
    max-width: 68px;
    text-align: right;
    /*margin-bottom: 0px;*/
  }
  .form-subtitle {
    width: 120px;
    padding: 0px 0 7px 0px;
    text-align: right;
    line-height: normal;
    font-size: 12px;
    color: #878d99;
  }

  .last-form-item {
    margin-bottom: 0px;
  }
</style>

<script>
  export default {
    name: 'StudentList',
    data () {
      return {
        form: {
          checkAll: false,
          students: [],
          checkedStudents: [],
          isIndeterminate: true
        }
      }
    },
    methods: {
      fetchStudentList () {
        // 加载数据
        let index = 0
        for (;index < 10; index++) {
          let item = {
            id: index,
            name: '张三'
          }
          this.form.students.push(item)
        }
      },
      handleCheckAllChange (val) {
        this.form.checkedStudents = val ? this.form.students : []
        this.form.isIndeterminate = false
      },
      handleCheckedStudentsChange (value) {
        let checkedCount = value.length
        this.form.checkAll = checkedCount === this.form.students.length
        this.form.isIndeterminate = checkedCount > 0 && checkedCount < this.form.students.length
      },
      onSubmit () {
      },
      onCancel () {
        this.$router.back()
      }
    },
    created () {
      this.fetchStudentList()
    }
  }
</script>
