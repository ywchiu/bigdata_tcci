# 台泥資訊Big Data 課程


## 啟用Cloudera Manager (獨立設置一台)

### 切換成 root 使用者
- $ su –

### 使用visudo 編輯權限
- $ visudo

### 給予sudo 權限 (於99行處)
- hadoop ALL=(ALL) ALL

### 啟用sudo 權限 
- $ service sshd restart

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
- $ sudo vi /etc/hosts
- $ sudo vi /etc/sysconfig/network
- $ sudo hostname cmaster

### 重啟伺服器
- $ sudo reboot

## 啟用Hadoop 機器




