
## 认证
   
---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/login](#login) | 用户登陆 |
| [/register](#register) | 用户注册 |


## 认证接口详情
---
* #### login
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| mail | string | 邮箱 |
| password | string |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "id": 0,
    "type": 0|1,
    "e-mail": xxxx,
    "name": xxxx
  }
}
```


---
* #### register
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| mail | string | 邮箱 |
| password | string |  |
| type | int | 0代表学生，1代表老师 |
| name | string | 用户姓名 |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "id": 0,
    "type": 0|1,
    "e-mail": xxxx,
    "name": xxxx
  }
}
```

## 与老师相关

---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/teacher/:id/course](#courseList) | 老师查看自己的课程列表 |
| [/teacher/:id/course](#createCourse) | 老师创建一门课程 |
| [/course/:id/exam](#courseExamList) | 某一门课程的考试列表 |
| [/course/:id/exam](#createCourseExam) | 创建某一门课程的考试 |
| [/exam/:id](#downLoadExam) | 下载某一门考试的试卷 |
| [/exam/:id/student](#examStudentList) | 某一次考试的学生列表 |
| [/exam/:id/papers](#downLoadStudentExamPaper) | 下载所有选中的学生考卷 |
| [/course/:id/question](#uploadQuestion) | 上传某一门课程的题库 |
| [/exam/:id/student](#uploadStudentList) | 上传某一次考试的考生列表 |

## 与老师相关接口详情
---
* #### courseList
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| teacherId | int | |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "courseList": [
      {
        "id": 0,
        "name": xxx
      }
    ]
  }
}
```

---
* #### createCourse
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| teacherId | int | |
| courseName | string |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    
  }
}
```
---
* #### courseExamList
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| courseId | int |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "examList": [
      {
        "id": 0,
        "name": xxx,
        "startTime": xxx,
        "endTime": xxx
      }
    ]
  }
}
```
---
* #### createCourseExam
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examName | string |  |
| startTime | string |  |
| endTime | string |  |
| studentListFileId | int | 上传完学生列表的文件，后台将列表文件存储，并为其编号，将编号结果返回给前端 |
| scoreList | arr | 分值列表，代表每道题设置的分值，列表项总数代表题目总数 |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
 
  }
}
```

---
* #### downLoadExam
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
     "fileUrl": xxxx
  }
}
```
---
* #### examStudentList
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
     "studentList": [
      {
        "id": 0,
        "name": xxx
      }
    ]
  }
}
```
---
* #### downLoadStudentExamPaper
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |
| studentIdList | arr | 选中的学生id列表 |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
     "fileUrl": xxxx
  }
}
```

---
* #### uploadQuestion
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| courseId | int |  |
| questionFile | file |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
     
  }
}
```

---
* #### uploadStudentList
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |
| studentListFile | file |  |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
     "id": 0
  }
}
```



## 与学生相关   
---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/student/:id/exam](#studentExamList) | 某学生查看自己未参加的考试列表 |
| [/student/:student_id/exam/:exam_id](#enterExam) | 某学生参加某次考试，输入验证码 |
| [/exam/:id](#examInfo) | 获得某次考试的基本信息 | 
| [/question/:id](#questionInfo) | 获得某道题目的信息 |
| [/student/:student_id/exam/:exam_id/submit](#examSubmit) | 某学生提交某次试卷 |

## 与学生相关接口详情
---
* #### studentExamList
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| id | int | |


##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "examList": [
      {
        "id": 0,
        "name": xxx
      }
    ]
  }
}
```

---
* #### enterExam
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| student_id | int | |
| exam_id | int | |
| code | string | 考试验证码 |

##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
  }
}
```

---
* #### examInfo
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| exam_id | int | |

##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "questionIdList": [1, 2, 3]
  }
}
```
---
* #### questionInfo
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| question_id | int | |

##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
    "question": xxxx,
    "type": 0(单选) | 1(多选),
    "optionList": [
      {
        "id": 0,
        "content": xxx
      }
    ]
  }
}
```

---
* #### examSubmit
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| student_id | int | |
| exam_id | int | |
| answer | arr | 答题卡，单选题答案为单个数字，多选题答案为数组，如果未作答则为 -1,形如 [1, 1, 2, [1, 3], [2, 4], 4, 2] |

##### 返回结果
```
{
  "code": 0,
  "msg": "",
  "data": {
   
  }
}
```


