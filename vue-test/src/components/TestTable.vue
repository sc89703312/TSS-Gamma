<template>
  <el-table :data="tableData">
    <el-table-column prop="date" label="日期" width="140">
    </el-table-column>
    <el-table-column prop="name" label="姓名" width="120">
    </el-table-column>
    <el-table-column prop="address" label="地址">
    </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: 'TestTable',
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
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }
        this.tableData = Array(id).fill(item)
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
