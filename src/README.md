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

src/
├── main/
│   ├── java/
│   │   ├── controller/              # 控制层：JavaFX 控制逻辑
│   │   ├── model/                   # 模型层：核心游戏逻辑
│   │   │   ├── block/               # Block 相关类
│   │   │   ├── board/               # Board 类及算法
│   │   │   ├── log/                 # 日志记录模块
│   │   │   ├── timer/               # 计时模块
│   │   │   ├── user/                # 用户系统模块
│   │   │   ├── ai/                  # AI 求解模块（预留）
│   │   │   ├── level/               # 关卡系统模块（预留）
│   │   │   ├── network/             # 网络通信模块（预留）
│   │   │   └── GameModel.java       # 主模型入口
│   │   ├── view/                    # JavaFX 视图组件
│   │   │   ├── StartView.java
│   │   │   ├── GameView.java
│   │   │   ├── VictoryView.java
│   │   │   └── components/          # 自定义 UI 组件（动画、按钮等）
│   │   ├── Main.java                # 程序入口
│   │   └── config/                  # 配置中心（常量、配置类）
│   │       ├── AppConfig.java
│   │       └── GameConstants.java
│   └── resources/                   # 资源文件（JSON、FXML、CSS、图片、音频）
│       ├── levels/                  # 关卡配置文件
│       ├── saves/                   # 存档文件
│       ├── logs/                    # 日志文件
│       ├── images/                  # 图像资源
│       ├── sounds/                  # 音频资源
│       └── css/                     # 样式表



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

## 类功能解读说明


## 开发进度大体记录
2025年5月10日:整理好文件架构，在model中引入json功能
2025年5月11日:
- 清理完JavaFX转换时遗留的bug
- 同时进一步修改使得main可以运行，但是我的界面和游戏呢？
- 正确添加JavaFX路径依赖后终于动起来了，但是键盘监听和地图加载呢？
- 感觉我在写bug，不管了先提交一个版本吧。
- 14:50:32 添加日志功能，记录运行时文件的输出，包括上面的键盘监听和地图加载，方便调试，也为以后做准备。改写为动态代理模式。
- 15:05:01 着手增加计时器与TimeAttack模式，着手修改前面莫名其妙的地图和键盘问题。
- 和同学聊天时了解到他们有做聊天功能。
- todo:idea里面新添加package和新建profile有什么区别？
- 15:13:26定位到了GameFrame，修改了默认构造方法，logs没有完全加入所以是空空的
- 15:20:55 学习使用 IDEA 的 bookmark 功能，成功使用 IDEA CodeWithMe 功能，对于本项目而言比 github 方便
- 16:10:48 加入动态代理的 timer 功能，但是显然只弄好了模型，还没有进入应用层，接下来准备先debug，让游戏跑起来。
- todo:完成动态代理机制，目前暂时不能写入文档。
- todo:增加计时功能，并一并写入用户存档文件？保存每一步设置并且读取。
- todo：一种算法求解思路：是否处于某一种标准解法的状态？没有？后退一步！
- todo: 学习 bookmark 的使用

## 功能更新备忘录
1. todo：JSON 日志格式建议（兼容未来功能）。[
   {
   "username": "player1",
   "action": "move_up",
   "timestamp": 1719823981231,
   "total_seconds": 123
   },
   {
   "username": "player1",
   "action": "save_game",
   "timestamp": 1719823981350,
   "total_seconds": 124
   }
   ]

[玩家点击“开始游戏”]
↓
[TimerManager.startTimer()]
↓
[GameTime 开始计时]
↓
[执行 move(), undo(), save()...]
↓
[timerManager.recordAction()]
↓
[timeLogger.logAction(...)]
↓
[写入 time_logs.json]
↓
[定时更新 UI 上的时间显示]

UI层显示时间
Label timeLabel = new Label();

Timeline timeline = new Timeline(
new KeyFrame(Duration.seconds(1), e -> {
int seconds = timerManager.getGameTime().getTotalSeconds();
timeLabel.setText(timerManager.getGameTime().getFormattedTotalTime());
})
);

timeline.setCycleCount(Timeline.INDEFINITE);
timeline.play();

Bonus,GameTime状态写入存档文件
{
"map": [[1,1,2,...]],
"steps": 23,
"time_used_seconds": 123
}

加载时恢复
gameTime.restoreFromSave(saveData.getInt("time_used_seconds") * 1000L);

动态代理技术中所有需要监控的东西都在GameOperation里面定义即可。
IDEA居然还能通话，这太全面了。

感觉EDA异步网络还是有点麻烦了，做个特别nb的单机也很好了。