<template>
  <div class="table-container">
    <el-row type="flex" :gutter="15" class="table-button">
      <el-col>
        <el-button icon="el-icon-plus" size="small" @click="addRecord">新建考试</el-button>
      </el-col>
      <el-col>
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :show-file-list="false">
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
        const item = {
          exam_id: '000001',
          date: '2016/07/11',
          name: '软件过程管理'
        }
        this.tableData = Array(id).fill(item)
      },

      addRecord () {
        console.log('创建新的一条记录')
        this.$router.push({name: 'CreateExam', params: {course_id: this.$route.params.course_id}})
      },

      uploadQuestion () {
        console.log('导入题库')
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
