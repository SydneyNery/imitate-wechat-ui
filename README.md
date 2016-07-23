# imitate-wechat-ui
we imitate wechat and create a fragment + viewpager frame

#仿微信主界面的Fragment + ViewPager的结构
其中fragment底部的指示标识可以根据左右滑动的距离来改变透明度。微信是用了两个上下叠加的图层然后控制上面图层的显示和消失来实现的，我这里偷懒直接控制图片和文字的透明度实现。代码中还包括一个自动播放的轮盘控件，可以用于常用的app顶部轮盘图片展示。
