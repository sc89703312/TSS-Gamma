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
                  <el-menu-item index="1-1">课程1</el-menu-item>
                  <el-menu-item index="1-2">课程2</el-menu-item>
                  <el-menu-item index="1-3">课程3</el-menu-item>
                  <el-menu-item index="1-4">课程4</el-menu-item>
                  <el-menu-item index="1-5">课程5</el-menu-item>
                  <el-menu-item index="1-6">课程6</el-menu-item>
                  <el-menu-item index="1-7">课程7</el-menu-item>
                </el-menu-item-group>
              </el-submenu>
              <!--添加课程-->
              <el-menu-item index="2">
                <el-row type="flex" align="middle">
                  <i class="el-icon-circle-plus-outline"></i>
                  <span>添加课程</span>
                </el-row>
              </el-menu-item>
          </el-menu>
        </el-row>
      </el-aside>

      <!--主内容-->
      <el-main>

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
    line-height: 560px;
  }

  .el-container:nth-child(2) {
    margin-top: 10px;
  }

</style>

<script>
  export default {
    data () {
      return {

      }
    },
    methods: {
      handleSelect (key, keyPath) {
        console.log('当前选中了index: ' + key)
      }
    }
  }
</script>
