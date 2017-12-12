<template>
  <div class="table-container">
    <el-row type="flex" :gutter="15" class="table-button">
      <el-col>
        <el-button icon="el-icon-plus" size="small" @click="addRecord">新建考试</el-button>
      </el-col>
      <el-col>
        <el-upload
          class="upload-demo"
          action="http://localhost:8080/file/upload"
          :show-file-list="false"
          :on-success="uploadSuccess"
          :on-error="uploadFail">
          <el-button icon="el-icon-upload" size="small" type="primary">上传题库</el-button>
        </el-upload>
      </el-col>
    </el-row>
    <div>
      <el-table :data="tableData">
        <el-table-column prop="exam_id" label="考试编号" width="120">
        </el-table-column>
        <el-table-column prop="date" label="考试日期" width="160">
        </el-table-column>
        <el-table-column prop="name" label="考试名称">
        </el-table-column>
        <el-table-column
          label=""
          width="120">
          <template slot-scope="scope">
            <el-button @click="viewExam(scope.row)" type="text" size="small">查看</el-button>
            <el-button @click="downloadExam(scope.row)" type="text" size="small">下载试题</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<style>
  .table-button {
    float: right;
  }

  .table-container {
    margin-top: 18px;
  }
</style>

<script>
  import ResourceTeacher from '@/services/teacher'
  export default {
    name: 'ExamTable',
    data () {
      return {
        tableData: []
      }
    },

    methods: {
      fetchExamList () {
        // 加载数据
        let id = parseInt(this.$route.params.course_id)
        let res = ResourceTeacher.courseExamList({courseId: id})
        this.tableData = res.data
      },

      addRecord () {
        console.log('创建新的一条记录')
        this.$router.push({name: 'CreateExam', params: {course_id: this.$route.params.course_id}})
      },

      uploadQuestion () {
        console.log('导入题库')
      },

      uploadSuccess (res, file, fileList) {
        console.log(res)
        this.$message({
          message: '文件 ' + res + ' 上传成功',
          type: 'success'
        })
      },

      uploadFail (res, file, fileList) {
        console.log(res)
        this.$message({
          message: res,
          type: 'error'
        })
      },

      viewExam (row) {
        console.log('当前选中了编号为 ' + row.exam_id + ' 的考试')
        this.$router.push({name: 'StudentList', params: {course_id: this.$route.params.course_id, exam_id: row.exam_id}})
      },

      downloadExam (row) {
        console.log('下载考试id为 ' + row.exam_id + ' 的试卷')
      }
    },

    watch: {
      // 在该组件的生命周期内,可能需要根据url中的course_id加载多次数据
      // 需要使用watch监听route的变化 修改数据内容
      $route () {
        this.fetchExamList()
      }
    },

    mounted () {
      // 在组件创建后调用加载数据的方法
      this.fetchExamList()
    }
  }
</script>
