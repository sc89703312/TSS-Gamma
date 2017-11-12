<template>
  <div class="paperContainer">
    <div class="answer-card">
      <el-table
        :data="tableData"
        @row-click="rowClick"
        :row-class-name="tableRowClassName"
        style="width: 100%">
        <el-table-column
          prop="index"
          label="题号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="answer"
          label="答案">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style>
  .paperContainer {
    margin: 0 auto;
    max-width: 950px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .answer-card {
    margin-top: 20px;
    border-top: 1px dashed #DDD;
    font-size: 16px;
    background: #FFF;
  }

  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>

<script>
  export default {
    name: 'AnswerGather',
    data () {
      return {
        tableData: []
      }
    },
    methods: {
      answerCardDetected () {
        this.tableData = []
        let answerContent = JSON.parse(this.$cookie.get('answerContent'))
        for (let index in answerContent) {
          let content = answerContent[index]
          let answer = content instanceof Array ? content.join('; ') : content
          this.tableData.push({index: (parseInt(index) + 1), answer: answer})
        }
      },
      rowClick (row, event, column) {
        let questionList = this.$cookie.get('questionList').split(',')
        let selectedQId = questionList[row.index - 1]
        this.$router.push({name: 'ExamPaper', params: {'student_id': this.$route.params.student_id, 'exam_id': this.$route.params.exam_id, 'q_id': selectedQId}})
      },
      tableRowClassName ({row, rowIndex}) {
        let index = parseInt(row.index) - 1
        let markedList = this.$cookie.get('markedList').split(',')
        let questionList = this.$cookie.get('questionList').split(',')
        let QId = questionList[index]
        if (markedList.indexOf(QId) === -1) {
          return ''
        } else {
          return 'warning-row'
        }
      }
    },
    mounted () {
      this.answerCardDetected()
    }
  }
</script>
