<template>
  <el-progress :text-inside="true" :stroke-width="15" :percentage="examProgress"></el-progress>
</template>
<script>
  export default {
    name: 'ExamPaper',
    data () {
      return {
      }
    },
    methods: {
    },
    computed: {
      examProgress: function () {
        let questionNum = this.$cookie.get('questionNum')
        let questionList = this.$cookie.get('questionList')
        let currentQId = this.$route.params.q_id
        let index = 1
        for (var item in questionList) {
          console.log(questionList[item])
          if (questionList[item] === currentQId) {
            break
          }
          index++
        }

//        let currentIndex = this.$cookie.get('questionList').indexOf(this.$route.params.q_id)
        console.log(index)
        let ret = (index / questionNum).toFixed(2) * 100
        return ret
      }
    }
  }
</script>
