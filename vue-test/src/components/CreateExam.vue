<template>
  <div v-loading="loading">
    <h4 class="form-title">新建考试</h4>
    <el-form class="create-exam-el-form" ref="form" :model="form" label-width="80px" size="medium" label-position="left">
      <el-form-item label="考试名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="考试时间">
        <el-date-picker
          v-model="form.date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="学生列表">
        <el-upload
          :action="uploadUrl"
          :on-success="uploadSuccess"
          :on-error="uploadFail">
          <el-button icon="el-icon-upload" type="success" size="small">点击上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="题数&分值">
        <el-input-number v-model="form.number" :min="1" :max="10" label="题目数量"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="10">
          <template v-for="index in form.number">
            <el-col :span="8" class="exam-question-score">
              <el-input
                placeholder="分值: 10"
                type="number"
                v-model.number="form.scores[index-1]">
              </el-input>
            </el-col>
          </template>
        </el-row>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" size="small" @click="onSubmit">立即创建</el-button>
        <el-button size="small" @click="onCancel">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style>
  .create-exam-el-form {
    width: 480px;
  }

  .exam-question-score {
    padding-bottom: 12px;
  }

  .form-title {
    text-align: left;
  }
</style>
<script>
  import ResourceTeacher from '@/services/teacher'
  import util from './../../static/utils'
  export default {
    name: 'CreateExam',
    data () {
      return {
        form: {
          name: '',
          date: '',
          number: '',
          scores: [],
          studentFile: ''
        },
        loading: false,
        uploadUrl: util.hostPort + '/file/upload'
      }
    },
    methods: {
      onSubmit () {
        console.log(this.form)
        let startTimeStr = this.form.date[0]
        let endTimeStr = this.form.date[1]
        let params = {
          courseId: this.$route.params.course_id,
          examName: this.form.name,
          startTime: startTimeStr,
          endTime: endTimeStr,
          studentListFile: this.form.studentFile,
          scoreList: this.form.scores
        }
        this.loading = true
        ResourceTeacher.courseExamCreate(params).then((res) => {
          console.log(res.data)
          this.loading = false
          this.$message({
            message: '考试 ' + res.data.id + ' 创建成功',
            type: 'success'
          })
          this.$router.push({name: 'TestTable', params: {course_id: this.$route.params.course_id}})
        }).catch((err) => {
          this.loading = false
          console.log(err)
          this.$message({
            message: '考试创建失败',
            type: 'error'
          })
        })
      },
      onCancel () {
        this.$router.back()
      },
      uploadSuccess (res, file, fileList) {
        this.form.studentFile = res.filename
      },
      uploadFail (res, file, fileList) {
        this.$message({
          message: '上传学生列表文件失败',
          type: 'error'
        })
      }
    }
  }
</script>
