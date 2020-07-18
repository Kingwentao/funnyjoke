# funnyjoke
a joke app for you funny everytime,everywhere

目标：使用JetPack和Kotlin，实现一个让你无时无刻享受它给你带来快乐的app

使用技术 ： JetPack + Kotlin

### 1. Navigation
### 2. Room
### 3. Paging
### 4. CameraX
### 5. WorkManager

### 包结构设计

- app: 业务层
- libcommon : 公共的基础组件
- libnetowrk：网络相关的基础组件
- libnavannotation：页面跳转的基础组件
- libnavcompiler： 注解相关的基础组件


#### libcommon

- util：常用的工具类
- view: 公用的view类
- AppGlobals: 全局的应用

#### libnetowrk

- cache： 可对网络请求的数据缓存到数据库
....: 网络请求需要的