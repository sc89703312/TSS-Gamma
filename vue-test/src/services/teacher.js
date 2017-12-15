import Axios from 'axios'

export default {
  courseList ({teacherId}) {
    return Axios.get('/teacher/' + teacherId + '/course')
  },
  createCourse ({teacherId, courseName}) {
    return Axios.post('/teacher/' + teacherId + '/course', {
      name: courseName
    })
  },
  courseExamList ({courseId}) {
    return Axios.get('/course/' + courseId + '/exam')
  },
  uploadQuestion ({courseId, questionFile}) {
    return Axios.post('/course/' + courseId + '/question', {
      questionFile: questionFile
    })
  },
  courseExamCreate ({courseId, examName, startTime, endTime, studentListFile, scoreList}) {
    return Axios.post('/course/' + courseId + '/exam', {
      name: examName,
      startTime: startTime,
      endTime: endTime,
      studentListFile: studentListFile,
      scoreList: scoreList
    })
  },
  downLoadExam ({examId}) {
    return Axios.post('/exam/' + examId + '/download')
  },
  examStudentList ({examId}) {
    return Axios.get('/exam/' + examId + '/students')
  },
  downLoadStudentExamPaper ({examId, studentIdList}) {
    return Axios.post('/exam/' + examId + '/papers', {
      studentIdList: studentIdList
    })
  }
}
