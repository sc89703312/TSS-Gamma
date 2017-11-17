// import Axios from 'axios'

export default {
  courseList ({teacherId}) {
    return {
      data: {}
    }
  },
  createCourse ({teacherId, courseName}) {
    return {
      data: {}
    }
  },
  courseExamList ({courseId}) {
    return {
      data: {}
    }
  },
  courseExamCreate ({courseName, startTime, endTime, studentListFileId, scoreList}) {
    return {
      data: {}
    }
  },
  downLoadExam ({examId}) {
    return {
      data: {}
    }
  },
  examStudentList ({examId}) {
    return {
      data: {}
    }
  },
  downLoadStudentExamPaper ({examId, studentIdList}) {
    return {
      data: {}
    }
  }
}
