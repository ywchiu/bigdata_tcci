# 台泥資訊Big Data 課程


### Cloudera Pseudo Distributed Mode 安裝說明
- https://www.youtube.com/watch?v=6WTbwb5mTSY
- https://www.youtube.com/watch?v=OXUFHcCxgPk
- https://www.youtube.com/watch?v=C_Pj-g5I60M
- https://www.youtube.com/watch?v=5PW2jx0vmXM
[![Everything Is AWESOME](http://img.youtube.com/vi/StTqXEQ2l-Y/0.jpg)](https://www.youtube.com/watch?v=StTqXEQ2l-Y "Everything Is AWESOME")

## 啟用Cloudera Manager (獨立設置一台)

### 切換成 root 使用者
- $ su –

### 修改密碼
- $ passwd


### 關閉selinux
- $ vi /etc/selinux/config
- SELINUX = disabled

### 關閉防火牆
- $ setup
- $ chkconfig iptables off

### 設定NTP Server
- $ service ntpd start

### 產生ssh key
- $ ssh-keygen -t rsa
- $ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authroized_keys

### 修改HOSTNAME
- $ ifconfig
- $ vi /etc/hosts
- $ vi /etc/sysconfig/network
- $ hostname cmaster

### 重啟伺服器
- $ reboot

### 安裝 Cloudera Manager (以root 登入)
- $ wget http://archive.cloudera.com/cm5/installer/latest/cloudera-manager-installer.bin
- $ chmod u+x cloudera-manager-installer.bin
- $ sudo ./cloudera-manager-installer.bin


## 啟用Hadoop 機器

- 使用帳號/密碼: admin/admin 登入

### 製作Parcels

- http://archive.cloudera.com/cdh5/parcels/
- 放置在 /opt/cloudera/parcel-repo



