# ECUTnet  
|    Star     |    Size     |   License    |
|   :---:     |   :---:     |   :---:      |
| ![GitHub Repo stars][star] | ![GitHub repo size][size] | ![GitHub][license]|

[star]: https://img.shields.io/github/stars/Olvi73/ECUT_Network_AutoLogin
[size]: https://img.shields.io/github/repo-size/Olvi73/ECUT_Network_AutoLogin
[license]: https://img.shields.io/github/license/Olvi73/ECUT_Network_AutoLogin

**使用方法**：  
+ **方法一(推荐)**  
    + 下载release版并运行   
+ **方法二**  
    + 导入外部jar包`hutool.jar`  
    + 运行MainFrame.java   
### 一、登录  
输入账号密码，选择对应的网络类型进行登录  
### 二、进阶功能
**记住密码** ：保存密码到当前文件夹，以加密方式储存  
**自动登录** ：打开软件后自动登录校园网  
**自动关闭** ：登录成功后，自动关闭软件（用于自动登录后的自动关闭软件）  
**注**：自动登录和自动关闭功能需在开启记住密码的情况下使用  

# 更新
~~2020/10/23更新后无法使用，需自行修改相关代码`mainframe.java`中的第330行左右 `wlan_user_ip` `callback`~~  

2020/10/27 已更新自动获取ip，打开即可使用

2020/11/18 更新注销/登录表单数据、界面设计

2020/11/19 界面icon，报错提示，加密方式更新

2020/12/3 通过http get以及正则表达式获取路由器wan 口ip(内网地址)
