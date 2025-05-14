# 开发日记
## 记录
让我们重现来时路

## bug起居注
记录格式 > 表象:原因:解决方案:消耗时间
- 选中方块容易错位:怀疑是this.selectedbox:没搞清楚this关键字的作用,学会不使用实例调用方法
- 修改地图matrix数据点觉得不对劲:getMatrix修改地图文件,然后数组reference导致了原始数据被修改:重写getMatrix和setMatrix方法
- restartGame按钮没有效果:mapModel的matrix一直在被改动,重置自身:另外保存OriginalMatrix
- ListenerPanel 抽象类居然没有子类:GamePanel里面具体重写业务代码:使用OOP重新设计
- IDEA找不到指定的类:out文件夹没有生成,需要编辑edit configuration:重新创建CS109Project并添加VMoption:30min
- 调试后发现,方块1地图没改写仍然是0,方块23没有正确加载,方块移动可以重叠且消失,有时突然卡住主动:initial和box对象初始化时占用周围格子为0,程序逻辑卡死:使用-1打墙发现,发现GameController里面moveblock写了两次,第二次是getId,于是会不正确加载?抄ai的好事.
- update,在不主动进行违法操作时，没有bug，进行某些违法操作时，系统可能会瘫痪,如，4*4block向左移动（本不可以）会很多报错，并且后续棋子移动出现问题:发现可能是边界超出索引,问题出现在view层:改写createBlock方法,使用setBlock方法,并且在GameController里面的moveBlock方法中添加了边界判断
- 棋子选中后红框不完全:可能是有重叠或者遮挡:已解决选中后红框不完全的问题，需要在选中状态时调用 bringToFront() 方法，将当前 BoxComponent 移动到父容器的最顶层.确保红框不会被其他棋子遮挡
- 步数记录问题：左下方步数记录到1后不能继续，右边步数一直为0 : 怀疑是重置了:左下角是 initialization的时候重置steps为0
- 键盘方向键操控了Restart和Load button 问题：选中棋子后，按方向键时不能移动棋子，但是改变了Restart 和 Load button 的选中状态（蓝框），是不是不应该被选中, update：已解决，通过设置按钮的 setFocusTraversable(false) 来禁用按钮的焦点，使得它们不会响应键盘焦点切换。
- json文件里面没有mapname和mapid字段:猜想在MapModel里面:构造方法忘记初始化了

## 疑问记录
- 为什么GameStateManager里LoadGame要重置步数？不是应该保存现有步数吗？
- GameStateManager作为单例类，是否应该在构造函数中初始化？还是在使用时再初始化？这个类怎么用啊


## 功能开发记录
- 2025年5月13日 
  - 基本功能可移动
  - 胜利条件检测与提示,步数限制失败条件检测与提示(部分完成),胜利与失败动画
  - 地图文件加载与保存(部分完成),修改文件管理逻辑,补充多地图预设
  - 时间限制模式(部分完成)
- 2025年5月14日
  - 调用外部api实现布局解法查询与动态演示,并提供当下解法查询(部分完成)(todo:可以移植到游戏本体,因为是全排布查询)
  - 游戏进度序列化存档与读取,实时自动保存与提示
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 
- 书语开发:连续选中方块, 标签 steps 更新,updateSteps的方法重构以实现方块移动的动画效果?



需要解决的问题
- restart按钮对清楚you win 文字效果不起作用
- 地图文件\选档UI,地图文件导入,地图文件导出,地图文件重命名,地图文件删除
- timeLabel显示位置,加入checklosecondition, 限时模式逻辑梳理
- 步数限制应该是和地图文件绑定的,而不是和游戏状态绑定的
- 加入自动保存功能后,之前添加的限时模式逻辑不够清楚.
- 如何使用GameStateManager来管理游戏状态?感觉目前好臃肿混乱,如何使用单例类?
- 通过smallTool查询最优解,然后testMap批量写入生成,如何先把图像化的布局,变成数组文件?写一个smallToolPro.
- 选档功能?可视化选择与预先读取,selectList


可供开发的功能
- 打包后重新本地化--从mapModel OriginMatrix的广义延伸.


