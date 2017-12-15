<template>
  <div class="paperContainer">
    <el-card id="card-container" class="box-card">
      <div slot="header" class="clearfix">
        <el-row type="flex" :gutter="20">
          <el-col :span="20">
            <el-progress :text-inside="true" :stroke-width="15" :percentage="examProgress"></el-progress>
          </el-col>
          <el-col :span="4">
            <span class="timer"><i class="el-icon-time"></i>  {{timer}}</span>
          </el-col>
        </el-row>
      </div>

      <div>
        <div class="question">【{{questionType}}】{{question}}</div>
        <template>
          <el-radio-group style="width: 100%" v-if="!multiple" v-model="answer" @change="selectAnswer">
            <template v-for="option in options">
              <el-col :span="24" class="option">
                <el-radio :label="option.index">{{option.content}}</el-radio>
              </el-col>
            </template>
          </el-radio-group>
          <el-checkbox-group style="width: 100%" v-if="multiple" v-model="answerList" @change="selectAnswer">
            <template v-for="option in options">
              <el-col :span="24" class="option">
                <el-checkbox :label="option.index">{{option.content}}</el-checkbox>
              </el-col>
            </template>
          </el-checkbox-group>
        </template>
        <el-button @click="markQ" type="warning" size="small" icon="el-icon-edit-outline">{{markFlag}}</el-button>
        <el-button @click="answerGather" type="success" size="small" icon="el-icon-document">汇总页面</el-button>

        <el-button-group style="float: right">
          <el-button @click="previousQ" v-if="previousQVisible" size="small" type="primary" icon="el-icon-arrow-left">上一页</el-button>
          <el-button @click="nextQ" v-if="nextQVisible" size="small" type="primary">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
        </el-button-group>
      </div>
    </el-card>
  </div>
</template>

<style>
  .timer {
    font-size: 14px;
    font-family: "微软雅黑 Light";
    /*float: right;*/
  }

  .paperContainer {
    margin: 0 auto;
    max-width: 950px;
  }

  .question {
    margin-bottom: 20px;
  }

  .option {
    margin-bottom: 20px;
    border: 1px solid #d4d4d4;
    border-radius: 2px;
    padding: 12px 20px 13px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 950px;
    border: 2px solid transparent;
  }
</style>

<script>
  import ResourceStudent from '@/services/student'
  export default {
    name: 'ExamPaper',
    data () {
      return {
        timer: '',
        answer: -1,
        answerList: [],
        multiple: true,
        question: '',
        options: [],
        isMarked: false,
        markFlag: '标记题目',
        answerCard: ['1'],
        activeName: '1'
      }
    },
    methods: {
      timerCal () {
        var date = new Date()
        var now = date.getTime()
        let endTime = this.$cookie.get('endTime')
        var endDate = new Date(endTime)
        var end = endDate.getTime()
        var leftTime = end - now
        var h, m, s
        if (leftTime >= 0) {
          h = Math.floor(leftTime / 1000 / 60 / 60 % 24)
          m = Math.floor(leftTime / 1000 / 60 % 60)
          s = Math.floor(leftTime / 1000 % 60)
        } else {
          h = 0
          m = 0
          s = 0
        }
        if (h < 10) {
          h = '0' + h
        }
        if (m < 10) {
          m = '0' + m
        }
        if (s < 10) {
          s = '0' + s
        }
        this.timer = h + ':' + m + ':' + s
        setTimeout(this.timerCal, 1000)
      },
      nextQ () {
        let questionList = this.$cookie.get('questionList').split(',')
        let currentQId = this.$route.params.q_id
        let nextIndex = questionList.indexOf(currentQId) + 1
        let nextQId = questionList[nextIndex]
        this.$router.push({name: 'ExamPaper', params: {student_id: this.$route.params.student_id, exam_id: this.$route.params.exam_id, q_id: nextQId}})
      },
      previousQ () {
        let questionList = this.$cookie.get('questionList').split(',')
        let currentQId = this.$route.params.q_id
        let nextIndex = questionList.indexOf(currentQId) - 1
        let nextQId = questionList[nextIndex]
        this.$router.push({name: 'ExamPaper', params: {student_id: this.$route.params.student_id, exam_id: this.$route.params.exam_id, q_id: nextQId}})
      },
      markQ () {
        this.isMarked = !this.isMarked
        document.getElementById('card-container').style.border = this.isMarked ? '2px solid #efb137' : '2px solid transparent'
        this.markFlag = this.isMarked ? '取消标记' : '标记题目'
        if (this.isMarked) {
          let markedList = this.$cookie.get('markedList').split(',')
          markedList.push(this.$route.params.q_id)
          this.$cookie.set('markedList', markedList)
        } else {
          let markedList = this.$cookie.get('markedList').split(',')
          markedList.splice(markedList.indexOf(this.$route.params.q_id), 1)
          this.$cookie.set('markedList', markedList)
        }
      },
      answerGather () {
        this.$router.push({name: 'AnswerPaper', params: {'student_id': this.$route.params.student_id, 'exam_id': this.$route.params.exam_id}})
      },
      selectAnswer (val) {
        let answerList = JSON.parse(this.$cookie.get('answerList'))
        let answerContent = JSON.parse(this.$cookie.get('answerContent'))
        let indexList = JSON.parse(this.$cookie.get('indexList'))
        let currentQId = this.$route.params.q_id
        let questionList = this.$cookie.get('questionList').split(',')
        let currentIndex = questionList.indexOf(currentQId)
        indexList[currentIndex] = val
        if (val instanceof Array) {
          let contentList = []
          let tempAnswerList = []
          for (var index in val) {
            contentList.push(this.options[val[index]].content)
            tempAnswerList.push(this.options[val[index]].id)
          }
          answerContent[currentIndex] = contentList
          answerList[currentIndex] = tempAnswerList
        } else {
          answerContent[currentIndex] = this.options[val].content
          answerList[currentIndex] = this.options[val].id
        }
        this.$cookie.set('answerList', JSON.stringify(answerList))
        this.$cookie.set('answerContent', JSON.stringify(answerContent))
        this.$cookie.set('indexList', JSON.stringify(indexList))
        console.log(this.$cookie.get('answerList'))
        console.log(this.$cookie.get('answerContent'))
        console.log(this.$cookie.get('indexList'))
      },
      markDetected () {
        let markedList = this.$cookie.get('markedList').split(',')
        if (markedList.indexOf(this.$route.params.q_id) === -1) {
          this.isMarked = false
          document.getElementById('card-container').style.border = '2px solid transparent'
          this.markFlag = '标记题目'
        } else {
          this.isMarked = true
          document.getElementById('card-container').style.border = '2px solid #efb137'
          this.markFlag = '取消标记'
        }
      },
      selectDetected () {
        let indexList = JSON.parse(this.$cookie.get('indexList'))
        let currentQId = this.$route.params.q_id
        let questionList = this.$cookie.get('questionList').split(',')
        console.log(questionList)
        console.log(typeof currentQId)
        let currentIndex = questionList.indexOf(currentQId)
        let recordSelect = indexList[currentIndex]
        console.log('recordSelect: ' + recordSelect)
        if (recordSelect === -1) {
          this.answer = -1
          this.answerList = []
        } else if (this.multiple) {
          this.answerList = recordSelect
        } else {
          this.answer = parseInt(recordSelect)
        }
      },
      fetchQuestionInfo () {
        ResourceStudent.questionInfo({questionId: this.$route.params.q_id}).then((res) => {
          let questionInfo = res.data
          this.question = questionInfo.question
          this.options = []
          let count = 0
          questionInfo.optionList.map((obj) => {
            this.options.push({
              id: obj.id,
              content: obj.content,
              index: count++
            })
          })
          this.multiple = questionInfo.type === 1
          this.selectDetected()
        })
      }
    },
    watch: {
      // 在该组件的生命周期内,可能需要根据url中的course_id加载多次数据
      // 需要使用watch监听route的变化 修改数据内容
      $route () {
        this.fetchQuestionInfo()
        this.markDetected()
      }
    },
    computed: {
      examProgress: function () {
        let questionNum = this.$cookie.get('questionNum')
        let questionList = this.$cookie.get('questionList').split(',')
        let currentQId = this.$route.params.q_id
        let currentIndex = questionList.indexOf(currentQId)
        let ret = ((currentIndex + 1) / questionNum).toFixed(2) * 100
        return ret
      },
      nextQVisible: function () {
        let currentQId = this.$route.params.q_id
        let questionList = this.$cookie.get('questionList').split(',')
        let questionNum = this.$cookie.get('questionNum')
        let currentIndex = questionList.indexOf(currentQId)
        if (currentIndex === questionNum - 1) {
          return false
        } else {
          return true
        }
      },
      previousQVisible: function () {
        let currentQId = this.$route.params.q_id
        let questionList = this.$cookie.get('questionList').split(',')
        let currentIndex = questionList.indexOf(currentQId)
        if (currentIndex === 0) {
          return false
        } else {
          return true
        }
      },
      questionType: function () {
        return this.multiple ? '多选' : '单选'
      }
    },
    mounted () {
      this.timerCal()
      this.fetchQuestionInfo()
      this.markDetected()
    }
  }
</script>
