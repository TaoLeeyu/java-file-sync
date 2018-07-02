# java语言实现文件同步

一、使用前的准备：   

    A机器ssh登录B机器无需输入密码；   
    
    加密方式选 rsa|dsa均可以，默认dsa

二、设置ssh步骤：

    假设 A （192.168.75.130）为源文件主机，B（192.168.75.129）为目标机；

1、登录A机器   

2、ssh-keygen -t [rsa|dsa]，或者 ssh-keygen,将会生成密钥文件和私钥文件 id_rsa,id_rsa.pub或id_dsa,id_dsa.pub    

3、将 .pub 文件复制到B机器的 .ssh 目录， 并 cat id_dsa.pub >> ~/.ssh/authorized_keys    

4、大功告成，从A机器登录B机器的目标账户，不再需要密码了；（直接运行 #ssh 192.168.75.129）    


#注意：    

.ssh 和authorized_keys 文件的权限问题    

如果上述设置不成功，则按一下步骤设置文件权限     


#先设置.ssh目录权限    

$ chmod 700 -R .ssh    

#后设置authorized_keys权限    

$ chmod 600 authorized_keys    



三、项目说明  

1、starwifi-master 该war包需要部署在源文件所在的主机上    

2、starwifi-slave  该war包需要部署在目标文件所在的主机上    

3、slave  通过quartz 定时 发http请求到master    

4、master 对比文件的md5信息，如果两个文件的md5值不一致，则说明该文件有改动，需要执行更新操作    

5、使用linux 自带的文件同步命令 rsync    

6、同步单个文件：rsync -avz /usr/local/nginx/html root@192.168.75.129:/usr/local/nginx/html    

7、同步文件夹：rsync -rcv /usr/local/nginx/html root@192.168.75.129:/usr/local/nginx/html
