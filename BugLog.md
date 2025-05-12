# BugLog 排查日记

-
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
- 
没有使用 this.box box对象没有传递到 this.selectedBox 里面



#### 3
private void setBlock(int row, int col, int blockId) {
mapModel.getMatrix()[row][col] = blockId;
switch (blockId) {
不是，全写成get方法之后程序是怎么跑起来的？看起来GameController应该还有一个原始版本，服了。
原本全是 get 方法到底怎么修改的地图啊？匪夷所思啊。
感觉像是直接修改对应值，但这不是private类型吗？原来是直接的变量引用，真是bug中bug能用了。。

基础不牢地动山摇，原来this.field只是为了表示成员变量的区别名，并不一定是instance专属。

## bug表象:原因:解决方案
- 修改地图matrix数据点觉得不对劲:getMatrix修改地图文件,然后数组reference导致了原始数据被修改:重写getMatrix和setMatrix方法
- restartGame按钮没有效果:mapModel的matrix一直在被改动,重置自身:另外保存OriginalMatrix
- ListenerPanel 抽象类居然没有子类:GamePanel里面具体重写业务代码:使用OOP重新设计