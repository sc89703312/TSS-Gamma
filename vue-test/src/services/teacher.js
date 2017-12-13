import Axios from 'axios'

export default {
  courseList ({teacherId}) {
    return Axios.get('/course', {
      headers: {
        UserId: teacherId
      }
    })
  },
  createCourse ({teacherId, courseName}) {
    return {
      data: {}
    }
  },
  courseExamList ({courseId}) {
    if (courseId === 1) {
      return {
        data: [
          {
            exam_id: '000001',
            date: '2016/07/11',
            name: '软件工程与计算期中考试'
          }
        ]
      }
    } else {
      return {
        data: [
          {
            exam_id: '000002',
            date: '2016/07/11',
            name: '软件过程管理第一单元测试'
          },
          {
            exam_id: '000003',
            date: '2016/07/11',
            name: '软件过程管理第二单元测试'
          }
        ]
      }
    }
  },
  courseExamCreate ({examName, startTime, endTime, studentListFileId, scoreList}) {
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
    let index = 0
    let students = []
    for (;index < 50; index++) {
      let item = {
        id: index,
        name: '张三'
      }
      students.push(item)
    }
    return {
      data: students
    }
  },
  downLoadStudentExamPaper ({examId, studentIdList}) {
    return {
      data: {}
    }
  }
}
