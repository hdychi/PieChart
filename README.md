# PieChart
一个简易的饼图绘制
## 效果图
![](https://github.com/hdychi/PieChart/blob/master/screenshot/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202018-08-06%20%E4%B8%8B%E5%8D%884.25.35.png)
## 使用
1、在project的build.gradle中添加
````
allprojects {
    ...
    repositories {
        ...
        jcenter() //添加jcenter仓库
    }
    ...
}
````
2、在需要使用PieChart的模块的build.gradle中添加
````
implementation 'com.hdychi:PieChart:1.0.0'
````    
3、在布局文件中引用PieChart控件
````
<com.hdychi.piechart.PieChart
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:id="@+id/pie_chart"/>
````        
4、添加条目
````
pieChart = findViewById(R.id.pie_chart);
pieChart.addItem(new PieChartItem("名字",1));
````