# 功能简介

微信登录，发文本动态，发图文动态，发视频动态，关注，转发，评论，点赞，收藏等等

# 技术栈

后端：Java，SpringBoot，MyBatis，MySQL，Redis

前端：uni-app

# 开发环境

后端：IntelliJ IDEA

前端：HbuilderX，微信开发者工具，QQ小程序开发者工具

# 项目运行

## 后端

1. 创建数据库，执行根目录中的live.sql，并修改artist表中的微信APPID等配置

![image.png](https://upload-images.jianshu.io/upload_images/1754553-996c58c6a5bd443c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

2. 使用IDEA导入live-backend-mp项目，并通过Modules的方式导入live-backend-common项目

![image.png](https://upload-images.jianshu.io/upload_images/1754553-b9c521cc05c9daf4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3. 修改application-dev中的MySQL，Redis，阿里云OSS配置

![image.png](https://upload-images.jianshu.io/upload_images/1754553-9f45dc596be980de.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/1754553-202694c75f360a9a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 前端

1. 使用Hbuilder导入项目

2. 修改manifest.json的APPID配置（微信小程序或QQ小程序）

![image.png](https://upload-images.jianshu.io/upload_images/1754553-ee5278336889869c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/1754553-d4d9f7f24328b0f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

3. 修改App.vue中的domain配置（修改为后端项目的路径）

![image.png](https://upload-images.jianshu.io/upload_images/1754553-26b1b3281e92399c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

4. 运行到微信小程序

![image.png](https://upload-images.jianshu.io/upload_images/1754553-25c7af0b1cd18477.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 项目运行截图

![image.png](https://upload-images.jianshu.io/upload_images/1754553-3a2dedff6107bf88.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-48a50832c0747ba3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-bb874010d96d0370.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-47e9a311b2a62266.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-7ecc799b0d23cf65.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-7d1a1e381f083297.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-bc5c11b96deecf52.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/1754553-6d871ddd0bfa7aa4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

