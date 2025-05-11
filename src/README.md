# CS109-SUSTech

## 尝试进行任务拆解
整体GUI参照植物大战僵尸的层次？
- 游戏登入
  - 初始介绍界面“拯救曹操”，美丽进度条
  - 
- 游戏初始化
  - game start interface
  - board initialzation
  - block color differentation
  - restart function
- 游戏正常功能
  - 游戏进行
    - block movement
    - button control
    - keyboard control
    - boundary detection
    - collsion detection
    - information recording : movements and username and passport
  - 游戏进程
    - game save
    - game load
    - game pause
  - 
- 游戏结束
  - victory condition
  - victory interface:
    - numbers of movement
    - prompt 
  - game over interface
  - score calculation
- 游戏文件
  - game data
    - game record
    - game history
    - game replays
    - game statistics
  - userdata
    - account
    - multi-user login
    - 

- resources(generals.io)
  - music
    - movement sounds
    - victory sounds and defeat souds
    - background music
    - start sounds
  - aftereffects
    - wining showing escapes/ victory
    - movement
    - 游戏进程控制的整体氛围
- 
- 
- GUI
  - game interface
    - game start interface
    - game over interface
    - game victory interface
  - game control
    - button control
    - keyboard control
  - game information
    - game data display
    - game record display
  - game setting
    - music setting
    - sound setting
    - background setting
- 多地图加载和地图文件保存
  - 地图文件格式
  - 预览与选择
  - 地图文件的加载和保存
  - 地图文件的编辑和修改
  - 地图文件的删除和清空

- AI对弈系统
- 奖励道具和额外难度系统
- 实现多人同时对战、开房间或者其他在线功能



images/           # 图像资源
│       ├── sounds/           # 音频资源
│       ├── fonts/            # 字体资源
│       ├── levels/           # 关卡配置文件
│       ├── saves/            # 存档文件
│       ├── css/              # 样式表
│       └── lang/            

## 项目说明
（施工中）

## 经验分享
数据存储的三重形式
1. 运行内存中
2. 类与对象中
3. 系统文件中

## 进阶工具与命令
- maven
- github
- Eclipse
- git add -u
- IDEA add as library
- IDEA ignore filetype

## 开发进度大体记录
2025年5月10日:整理好文件架构，在model中引入json功能
2025年5月11日:
- 清理完JavaFX转换时遗留的bug
- 同时进一步修改使得main可以运行，但是我的界面和游戏呢？
- 正确添加JavaFX路径依赖后终于动起来了，但是键盘监听和地图加载呢？
- 感觉我在写bug，不管了先提交一个版本吧。

