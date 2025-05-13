# BugLog 排查日记

#### 1
```java
public void setSelectedBox(BoxComponent box) {
if (this.selectedBox != null) {
this.selectedBox.setSelected(false);
}
this.selectedBox = box;
if (box != null) {
box.setSelected(true);
}
}
```
没有使用 this.box box对象没有传递到 this.selectedBox 里面


#### 3
private void setBlock(int row, int col, int blockId) {
mapModel.getMatrix()[row][col] = blockId;
switch (blockId) {
不是，全写成get方法之后程序是怎么跑起来的？看起来GameController应该还有一个原始版本，服了。
原本全是 get 方法到底怎么修改的地图啊？匪夷所思啊。
感觉像是直接修改对应值，但这不是private类型吗？原来是直接的变量引用，真是bug中bug能用了。。

基础不牢地动山摇，原来this.field只是为了表示成员变量的区别名，并不一定是instance专属。

## bug表象:原因:解决方案:消耗时间
- 修改地图matrix数据点觉得不对劲:getMatrix修改地图文件,然后数组reference导致了原始数据被修改:重写getMatrix和setMatrix方法
- restartGame按钮没有效果:mapModel的matrix一直在被改动,重置自身:另外保存OriginalMatrix
- ListenerPanel 抽象类居然没有子类:GamePanel里面具体重写业务代码:使用OOP重新设计
- IDEA找不到指定的类:out文件夹没有生成,需要编辑edit configuration:重新创建CS109Project并添加VMoption:30min
- 方块移动可以重叠且消失:initial和box对象初始化时占用周围格子为0

调试后发现,方块1地图没改写仍然是0,方块23没有正确加载
发现GameController里面moveblock写了两次,第二次是getId,于是会不正确加载?抄ai的好事.




#### 4
运行游戏时问题
1 棋子移动问题：有些棋子不能移动，有些棋子往某些方向移动需要连续两次按动方向键
update：在不主动进行违法操作时，没有bug，进行某些违法操作时，系统可能会瘫痪
如，4*4block向左移动（本不可以）会很多报错，并且后续棋子移动出现问题
2 棋子选中问题：棋子选中后红框不完全，选中后移动一步选中便自动解除（want：保持选中状态）
update：已解决选中后红框不完全的问题，需要在选中状态时调用 bringToFront() 方法，将当前 BoxComponent 移动到父容器的最顶层。 确保红框不会被其他棋子遮挡

3 步数记录问题：左下方步数记录到1后不能继续，右边步数一直为0

4 键盘方向键操控了Restart和Load button 问题：选中棋子后，按方向键时不能移动棋子，但是改变了Restart 和 Load button 的选中状态（蓝框），是不是不应该被选中？？
update：已解决，通过设置按钮的 setFocusTraversable(false) 来禁用按钮的焦点，使得它们不会响应键盘焦点切换。
5 有一些棋子可以进行非法移动，如1*1block会移动到1*2block里并且消失
疑问：为什么GameStateManager里LoadGame要重置步数？不是应该保存现有步数吗？