<template>
  <el-container>
    <!--头部-->
    <el-header>TSS-Gamma</el-header>

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
                    <span>课程列表</span>
                  </el-row>
                </template>
                <el-menu-item-group>
                  <template slot="title">课程</template>
                  <el-menu-item v-for="item in items" :index="item.index" :key="item.key">
                    {{item.name}}
                  </el-menu-item>
                </el-menu-item-group>
              </el-submenu>
              <!--添加课程-->
              <el-menu-item index="create">
                <el-row type="flex" align="middle">
                  <i class="el-icon-circle-plus-outline"></i>
                  <span>添加课程</span>
                </el-row>
              </el-menu-item>
          </el-menu>
        </el-row>
      </el-aside>

      <!--主内容-->
      <el-main class="mail-container">
        <router-view></router-view>
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
    max-height: 520px;
  }
</style>

<script>
  export default {
    name: 'TeacherHome',
    data () {
      return {
        items: []
      }
    },
    methods: {
      handleSelect (index, keyPath) {
        console.log('当前选中了index: ' + index)
        if (index === 'create') {
          this.$router.push({name: 'CreateTable'})
        } else {
          this.$router.push({name: 'TestTable', params: {course_id: index}})
        }
      },
      fetchCourseList () {
        // 将来会在这些方法里面做数据加载 调用services中文件
        let data = [
          {name: '课程1', index: '1', key: '1'},
          {name: '课程2', index: '2', key: '2'}
        ]
        this.items = data
      }
    },
    mounted () {
      //  mounted在组件创建完成后执行,加载数据用,注意mounted方法在组件生命周期中只加载一次
      //  如果需要根据url加载数据,则需要对route做监听
      this.fetchCourseList()
    }
  }
</script>
