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

#### 2
restart game没有重置模型，没有保存原始模型

#### 3
private void setBlock(int row, int col, int blockId) {
mapModel.getMatrix()[row][col] = blockId;
switch (blockId) {
不是，全写成get方法之后程序是怎么跑起来的？看起来GameController应该还有一个原始版本，服了。
原本全是 get 方法到底怎么修改的地图啊？匪夷所思啊。
感觉像是直接修改对应值，但这不是private类型吗？原来是直接的变量引用，真是bug中bug能用了。。

基础不牢地动山摇，原来this.field只是为了表示成员变量的区别名，并不一定是instance专属。

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