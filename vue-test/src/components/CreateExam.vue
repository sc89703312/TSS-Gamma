<template>
  <div>
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
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="学生列表">
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/">
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
  export default {
    name: 'CreateExam',
    data () {
      return {
        form: {
          name: '',
          date: '',
          number: '',
          scores: []
        }
      }
    },
    methods: {
      onSubmit () {
        console.log(this.form.scores)
      },
      onCancel () {
        this.$router.back()
      }
    }
  }
</script>
