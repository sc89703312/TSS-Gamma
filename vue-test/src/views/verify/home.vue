<template>
  <div class="verify-box-card">
    <h1 class="verify-title">TSS-Gamma</h1>
    <el-card>
      <div>
        <el-form size="small" class="login-verify-form" label-position="top" label-width="60px" :model="form">
          <el-form-item>
            <span>用户名 | 邮箱</span>
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item>
            <span>密码</span>
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="login" class="verify-btn" type="success">
              登录
            </el-button>
          </el-form-item>
          <router-link class="register-link" :to="{name: 'Register'}">还没有账号?创建一个</router-link>
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

  .login-verify-form {
    padding: 0 6px 0;
  }

  .verify-btn {
    width: 100%;
    margin-top: 15px;
  }

  .verify-title {
    font-family: "Helvetica Neue";
    font-weight: lighter;
    padding: 0 auto;
    text-align: center;
  }

  .register-link {
    color: #0366d6;
    text-decoration: none;
    width: 100%;
    font-size: 10px;
    float: right;
    text-align: right;
    margin: 5px 0 20px 0;
  }
</style>

<script>
  import ResourceVerify from '@/services/verify'
  import { Message } from 'element-ui'
  export default {
    name: 'VerifyHome',
    data () {
      return {
        form: {
          email: '',
          password: '',
          type: ''
        }
      }
    },
    methods: {
      login () {
        let params = {
          email: this.form.email,
          password: this.form.password
        }
        ResourceVerify.login(params).then((res) => {
          console.log('success')
          if (params.email === '1111@nju.edu.cn') {
            this.$router.push({name: 'TeacherHome', params: {teacher_id: 1}})
          } else {
            this.$router.push({name: 'StudentHome', params: {student_id: 1}})
          }
          this.$cookie.set('user_id', 1)
        }).catch((err) => {
          console.log('err')
          let errMsg = err.response.data.message
          let options = {
            message: errMsg
          }
          Message.error(options)
        })
      }
    }
  }
</script>
