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