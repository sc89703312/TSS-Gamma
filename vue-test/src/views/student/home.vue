<template>
  <el-container>
    <!--头部-->
    <el-header>
      TSS-Gamma
      <router-link class="exit-link" :to="{name: 'Login'}">退出登录</router-link>
    </el-header>

    <el-container>
      <!--侧边栏-->
      <el-aside width="200px">
        <el-row>
          <el-menu
            default-active="1-1"
            class="el-menu-vertical-demo"
            @select="handleSelect">
            <!--课程列表-->
            <el-submenu index="1">
              <template slot="title">
                <el-row type="flex" align="middle">
                  <i class="el-icon-location"></i>
                  <span>考试列表</span>
                </el-row>
              </template>
              <el-menu-item-group>
                <template slot="title">未参加的考试</template>
                <el-menu-item v-for="item in items" :index="item.index" :key="item.key">
                  {{item.name}}
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-row>
      </el-aside>

      <!--主内容-->
      <el-main class="mail-container">
        <router-view></router-view>
        <el-dialog
          title="请填写考试密码"
          :visible.sync="pwdDialogVisible"
          width="30%">
          <el-input v-model="pwd"></el-input>
          <span slot="footer">
            <el-button @click="pwdDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="verifyExamCode">确 定</el-button>
          </span>
        </el-dialog>
      </el-main>

    </el-container>
  </el-container>
</template>

<style>
  .el-header {
    color: #ffffff;
    background-color: #3d444b;
    line-height: 60px;
    padding-left: 28px;
  }

  .el-aside {
    color: #333;
    line-height: 600px;
  }

  .el-main {
    color: #333;
  }

  .el-container:nth-child(2) {
    margin-top: 10px;
  }

  .mail-container {
    margin-left: 100px;
    margin-right: 100px;
    /*max-height: 520px;*/
  }

  .exit-link {
    color: #fff;
    text-decoration: none;
    float: right;
    font-size: 12px;
  }
</style>

<script>
  import ResourceStudent from '@/services/student'
  export default {
    name: 'StudentHome',
    data () {
      return {
        items: [],
        pwdDialogVisible: false,
        pwd: '',
        currentIndex: ''
      }
    },
    methods: {
      handleSelect (index, keyPath) {
        console.log('当前选中了index: ' + index)
        this.$router.push({name: 'StudentHome', params: {student_id: this.$route.params.student_id}})
        this.currentIndex = index
        this.pwdDialogVisible = true
      },
      fetchExamList () {
        // 将来会在这些方法里面做数据加载 调用services中文件
        let id = this.$route.params.student_id
        this.items = ResourceStudent.examList({studentId: id}).data
      },
      verifyExamCode () {
        console.log('exam code : ' + this.pwd)
        this.pwdDialogVisible = false
        this.fetchExamInitData()
        this.$router.push({name: 'ExamPaper',
          params: {
            student_id: this.$route.params.student_id,
            exam_id: this.currentIndex,
            q_id: this.$cookie.get('questionList')[0]
          }
        })
      },
      fetchExamInitData () {
        let questionList = ['1', '2', '3', '4', '5', '6']
        let questionNum = questionList.length
        let answerList = []
        let answerContent = []
        questionList.map(function (value, index) {
          answerList.push(-1)
          answerContent.push('')
        })
        this.$cookie.set('questionList', questionList)
        this.$cookie.set('questionNum', questionNum)
        this.$cookie.set('markedList', [])
        this.$cookie.set('answerList', JSON.stringify(answerList))
        this.$cookie.set('answerContent', JSON.stringify(answerContent))
      }
    },
    mounted () {
      //  mounted在组件创建完成后执行,加载数据用,注意mounted方法在组件生命周期中只加载一次
      //  如果需要根据url加载数据,则需要对route做监听
      this.fetchExamList()
    }
  }
</script>
