<template>
  <div>
    <h4 class="form-title">考卷生成</h4>
    <el-form class="exam-paper-el-form" ref="form" :model="form" label-width="80px" label-position="left">
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
  .exam-paper-el-form {
    width: 100%;
  }

  .form-title {
    text-align: left;
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
  import ResourceTeacher from '@/services/teacher'
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
        let examId = this.$route.params.exam_id
        this.form.students = ResourceTeacher.examStudentList({examId: examId}).data
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
