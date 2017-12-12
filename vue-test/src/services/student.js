// import Axios from 'axios'

export default {
  examList ({studentId}) {
    return {
      data: [
        {name: '软工II期末考试', index: '1', key: '1'},
        {name: '软件过程单元测试', index: '2', key: '2'}
      ]
    }
  },
  enterExam ({studentId, code}) {
    return {
      data: {}
    }
  },
  examInfo ({examId}) {
    return {
      data: {}
    }
  },
  questionInfo ({questionId}) {
    return {
      data: {}
    }
  },
  examSubmit ({studentId, examId, answerList}) {
    return {
      data: {}
    }
  }
}
