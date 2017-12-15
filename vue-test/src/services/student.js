import Axios from 'axios'

export default {
  examList ({studentId}) {
    return Axios.get('/student/' + studentId + '/exam')
  },
  enterExam ({studentId, code, examId}) {
    return Axios.post('/student/' + studentId + '/exam/' + examId, {
      code: code
    })
  },
  examInfo ({examId}) {
    return Axios.get('/exam/' + examId)
  },
  questionInfo ({questionId}) {
    return Axios.get('/question/' + questionId)
  },
  examSubmit ({studentId, examId, answerList}) {
    return Axios.post('/student/' + studentId + '/exam/' + examId + '/submit', {
      answer: answerList
    })
  }
}
