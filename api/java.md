
#jstat
    1.jstat  -gcutil 30894 5 10 总计
    2.jstat -gcnewcapacity 19570 新生代内存统计
    3.jstat -gccapacity 19570 堆内存统计
    4.jstat -compiler 19570 编译统计
    5.jstat -class 19570 类加载统计
    
    ref: https://www.cnblogs.com/sxdcgaq8080/p/11089841.html

# jmap
    查看jvm内存使用状况：
       jmap -heap pid
    查看jvm内存存活的对象：
        jmap -histo:live pid
    把heap里所有对象都dump下来，无论对象是死是活：
        jmap -dump:format=b,file=xxx.hprof pid
    先做一次full GC，再dump，只包含仍然存活的对象信息：
        jmap -dump:format=b,live,file=xxx.hprof pid


        
    