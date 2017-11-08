import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import TeacherHome from '@/views/teacher/home'
import ExamTable from '@/components/ExamTable'
import CreateCourse from '@/components/CreateCourse'
import CreateExam from '@/components/CreateExam'
import StudentList from '@/components/StudentList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: TeacherHome,
      children: [
        {
          path: '/course/create',
          name: 'CreateTable',
          component: CreateCourse
        },
        {
          path: '/course/:course_id',
          name: 'TestTable',
          component: ExamTable
        },
        {
          path: '/course/:course_id/exam/create',
          name: 'CreateExam',
          component: CreateExam
        },
        {
          path: '/course/:course_id/exam/:exam_id',
          name: 'StudentList',
          component: StudentList
        }
      ]
    }
  ]
})
