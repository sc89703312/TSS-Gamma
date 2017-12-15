# TSS-Gamma 前后端对接api

## 部署
有四部分需要部署
1. jar包
2. db文件
3. excels文件夹, 里边是excel模板
4. dist文件夹, 里边是静态资源

## 修改历史
1. 创建考试的返回值，参数`courseName`改为`name`
2. 上传文件url改为`file/upload`
3. 各类返回的json为列表的，直接返回列表，比如
```js
[
   {"name":"aa"},
   .....
]
```
4. 传参和返回值默认都按json传，即使只有一个参数或者返回值。上传文件接口除外，那个需要用表单传参。
5. exam_info返回值中添加更多信息，如开始时间，结束时间，考试名称
6. question_info返回值中，question添加`id`字段.
7. upload_file接口中，参数`upload_file`改名为`file`
8. download_exam下载某一门考试的试卷的url从`/exam/:id`改为`/exam/:id/download`.
9. 学生提交试卷的接口方法改为POST，返回描述变更
10. 新增获取课程题目数量的接口
11. 查询考试学生接口,新增学生score字段,为null表示学生还没交卷

## 认证
   
---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/login](#login) `done` | 用户登陆 |
| [/register](#register) `done` | 用户注册 |


## 认证接口详情
---
* #### login
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| email | string | 邮箱 |
| password | string |  |


##### 返回结果
```
{
    "id": 0,
    "type": 0|1,
    "email": xxxx,
    "name": xxxx
}
```


---
* #### register
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| email | string | 邮箱 |
| password | string |  |
| type | int | 0代表学生，1代表老师 |
| name | string | 用户姓名 |
| number | string | 当注册账号为学生时，需要填写学号 |


##### 返回结果
```
{
    "id": 0,
    "type": 0|1,
    "email": xxxx,
    "name": xxxx
}
```

## 与老师相关

---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/teacher/:id/course](#course_list) `done` | 老师查看自己的课程列表 |
| [/teacher/:id/course](#create_course) `done` | 老师创建一门课程 |
| [/course/:id/exam](#course_exam_list) `done` | 某一门课程的考试列表 |
| [/course/:id/exam](#create_course_exam) `done` | 创建某一门课程的考试 |
| [/exam/:id/student](#exam_student_list) `done` | 某一次考试的学生列表 |
| [/exam/:id/download](#download_exam) | 下载某一门考试的试卷 |
| [/exam/:id/papers](#download_student_exam_paper) | 下载所有选中的学生考卷 |
| [/course/:id/question](#upload_question) `done` | 上传某一门课程的题库 |
| [/course/:id/question/count](#question_count) | 获得课程的question数量 |
| [file/upload](#upload_file) `done` | 上传文件(通用) |

## 与老师相关接口详情
---
* #### course_list
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| teacherId | int | |


##### 返回结果
```
[
  {
     "id": 0,
     "name": xxx
  }
]

```

---
* #### create_course
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| teacherId | int | |
| courseName | string |  |


##### 返回结果
```
{}
```
---
* #### course_exam_list
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| courseId | int |  |


##### 返回结果
```
[
   {
     "id": 0,
     "name": xxx,
     "startTime": xxx,
     "endTime": xxx
   }
]

```
---
* #### create_course_exam
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examName | string |  |
| startTime | string |  |
| endTime | string |  |
| studentListFile | string | 学生列表文件名 |
| scoreList | arr | 分值列表，代表每道题设置的分值，列表项总数代表题目总数 |


##### 返回结果
```
{}
```

---
* #### download_exam
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |


##### 返回结果
```
{
     "fileUrl": xxxx
}
```
---
* #### exam_student_list
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| examId | int |  |


##### 返回结果
```
[
   {
     "id": 0,
     "name": xxx
   }
]

```
---
* #### download_student_exam_paper
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
     "fileUrl": xxxx
}
```

---
* #### upload_question
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| courseId | int |  |
| questionFile | string |  |


##### 返回结果
```
{}
```

---
* #### question_count
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| courseId | int |  |


##### 返回结果
```
{
   "questionCount": 10
}
```

---
* #### upload_file
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| file | file |  |


##### 返回结果
```
{
   "filename": xxx
}
```



## 与学生相关   
---

#### 接口列表

| url | 用途 |
|:-------------|:-------------|
| [/student/:id/exam](#student_exam_list) `done` | 某学生查看自己未参加的考试列表 |
| [/student/:student_id/exam/:exam_id](#enter_exam) `done` | 某学生参加某次考试，输入验证码 |
| [/exam/:id](#exam_info) `done` | 获得某次考试的基本信息 | 
| [/question/:id](#question_info) `done` | 获得某道题目的信息 |
| [/student/:student_id/exam/:exam_id/submit](#exam_submit)  `done` | 某学生提交某次试卷 |

## 与学生相关接口详情
---
* #### student_exam_list
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| id | int | |


##### 返回结果
```
[
   {
     "id": 0,
     "name": xxx
   }
]
```

---
* #### enter_exam
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
{}
```

---
* #### exam_info
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| exam_id | int | |

##### 返回结果
```
{
    "name": xxx,
    "startTime": "yyyy-MM-dd hh:mm:ss",
    "endTime": "yyyy-MM-dd hh:mm:ss",
    "questionIdList": [1, 2, 3]
}
```
---
* #### question_info
##### 请求方法
GET
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| question_id | int | |

##### 返回结果
```
{
    "id": 0
    "question": xxxx,
    "type": 0(单选) | 1(多选),
    "optionList": [
      {
        "id": 0,
        "content": xxx
      }
    ]
}
```

---
* #### exam_submit
##### 请求方法
POST
##### 请求参数
| 参数名称 | 参数类型 | 说明 |
|:-------------|:-------------|:-------------|
| student_id | int | |
| exam_id | int | |
| answer | obj | 答题卡，答案为数组，如果未作答则为空数组,形式见下边 |
```js
{
   1 : [2,4],  // questionId, [choiceId...]
   2 : [5],
   3 : []
}  
```
##### 返回结果
```
{}
```


