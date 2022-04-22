## IDEA部署Mybatis源码环境
流程：
1、下载mybatis-3 + mybatis-parent源码，注意对应版本
<a href="https://github.com/mybatis/mybatis-3.git"> https://github.com/mybatis/mybatis-3.git</a>
<a href="https://github.com/mybatis/parent">https://github.com/mybatis/parent</a>
![注意对应版本](https://img-blog.csdnimg.cn/20210306134228899.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
2、本地创建一个maven工程，将下载好的源码丢进去
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306134522625.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
3、注意MySQL版本，有需要的话替换mybatis-3的pom文件中的mysql依赖
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306135053361.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
替换成本地有的版本：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306135136592.png)
不然可能报下图异常：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306135216564.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
4、创建测试代码
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306135333932.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQ0ODcyNzg3,size_16,color_FFFFFF,t_70)
具体代码就不贴了，都准备看源码了  写个demo不是so easy

5、如果run的时候报time zone异常，在数据库url后面加serverTimeZone=GMT即可
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210306135706288.png)
