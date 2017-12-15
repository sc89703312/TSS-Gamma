<template>
  <div>
    <h4 class="form-title">考卷生成</h4>
    <el-form class="exam-paper-el-form" ref="form" :model="form" label-width="80px" label-position="left">
      <el-form-item label="学生列表" class="last-form-item">
        <el-checkbox-group v-model="form.checkedStudents">
          <el-checkbox :disabled="student.flag" style="margin-left: 0px; margin-right: 30px" v-for="student in form.students" :label="student" :key="student.id">{{student.name}} - {{student.flag ? '未出分' : student.score + '分'}}</el-checkbox>
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
  import util from './../../static/utils'
  export default {
    name: 'StudentList',
    data () {
      return {
        form: {
          students: [],
          checkedStudents: []
        }
      }
    },
    methods: {
      fetchStudentList () {
        // 加载数据
        let examId = this.$route.params.exam_id
        let params = {
          examId: examId
        }
        ResourceTeacher.examStudentList(params).then((res) => {
          this.form.students = []
          this.form.readyStudents = []
          let studentList = res.data
          studentList.map((obj) => {
            this.form.students.push({
              index: obj.id + '',
              id: obj.id,
              name: obj.name,
              score: obj.score,
              flag: obj.score == null
            })
          })
        })
      },
      onSubmit () {
        console.log(this.form.checkedStudents)
        let studentIdList = []
        this.form.checkedStudents.map((obj) => {
          studentIdList.push(obj.id)
        })
        ResourceTeacher.downLoadStudentExamPaper({examId: this.$route.params.exam_id, studentIdList: studentIdList}).then((res) => {
          let fileUrl = res.data.fileUrl
          let url = util.hostPort + fileUrl
          var newTab = window.open('about:blank')
          newTab.location.href = url
        })
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
