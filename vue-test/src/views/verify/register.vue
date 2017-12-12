<template>
  <div class="verify-box-card">
    <h1 class="verify-title">TSS-Gamma</h1>
    <el-card>
      <div>
        <el-form size="small" class="register-verify-form" label-position="right" label-width="70px" :model="form">
          <el-form-item label="用户类型">
            <el-radio-group v-model="form.type">
              <el-radio-button label="老师"></el-radio-button>
              <el-radio-button label="学生"></el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="用户名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item v-if="userType" label="学号">
            <el-input v-model="form.number"></el-input>
          </el-form-item>
          <el-form-item label="注册邮箱">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="form.region"></el-input>
          </el-form-item>
          <el-button @click="registerAndLogin" class="verify-btn" type="primary">
            注册并登录
          </el-button>
          <router-link class="register-link" :to="{name: 'Login'}">已有账户,直接登陆</router-link>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .verify-box-card {
    width: 312px;
    margin: 120px auto;
  }

  .register-verify-form {
    padding: 0 6px 0;
  }

  .verify-btn {
    width: 100%;
    margin-top: 15px;
    margin-bottom: 10px;
  }

  .verify-title {
    font-family: "Helvetica Neue";
    font-weight: lighter;
    padding: 0 auto;
    text-align: center;
  }
</style>

<script>
  export default {
    name: 'Register',
    data () {
      return {
        form: {
          name: '',
          region: '',
          type: '学生',
          email: '',
          number: ''
        }
      }
    },
    methods: {
      registerAndLogin () {
        if (this.form.type === '学生') {
          this.$router.push({name: 'StudentHome', params: {student_id: 1}})
        } else {
          this.$router.push({name: 'TeacherHome'})
        }
      }
    },
    computed: {
      userType: function () {
        return this.form.type === '学生'
      }
    }
  }
</script>
