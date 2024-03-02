### 概述

#### 1. 开发中的实际场景

> 1. 备份
> 
> 2. 代码还原
> 
> 3. 协同开发
> 
> 4. 追溯编写人和编写时间

#### 2. 版本控制器的方式

> 1. 集中式版本控制工具，例如 SVN
> 
> 2. 分布式版本控制工具，例如Git
>    
>    不存在“中央服务器”，每个电脑上都有一个完整的版本库，即共享版本库。

#### 3. Git

> 1. Git Bash: Git提供的命令行工具。
> 
> 2. Git GUI ：Git提供的图形界面工具。

##### 3.1 环境配置

设置用户信息

```
git config --global user.name"sangsang"
git config --global user.email"sangjicuoqinghua@163.com"
```

查看用户信息

```
git config --global user.name
git config --global user.email
```

##### 3.2 获取本地仓库

> 1. 在电脑的任意位置创建一个空目录（例如myGit）作为我们的本地仓库
> 
> 2. 进入这个目录，点击右键打开Git Bash窗口
> 
> 3. 执行命令git init
> 
> 4. 执行成功后可以在文件夹下看到隐藏的.git目录
>    
>    

##### 3.3 基础操作命令

> 1. git status 查看修改的状态
> 
> 2. git add .   添加工作区到暂存区（.表示添加所有到暂存区，也可以指定文件 git add file011.txt)
> 
> 3. git commit -m "注释内容"  添加暂存区到仓库
> 
> 4. git log 查看提交日志

> ![e34ac912-bc82-4924-90c5-c7964a37e63f](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/e34ac912-bc82-4924-90c5-c7964a37e63f.png)

> ![ca2e0183-0b63-4df2-8d7f-f11e0c6e557f](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/ca2e0183-0b63-4df2-8d7f-f11e0c6e557f.png)



> ![1c04b529-f8ec-4ac3-b406-e566f48d9e96](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/1c04b529-f8ec-4ac3-b406-e566f48d9e96.png)



> ![36cb8881-4496-487f-a9be-a5a734c6dc8e](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/36cb8881-4496-487f-a9be-a5a734c6dc8e.png)

##### 3.4 版本回退

> git reset --hard commitID
> 
> commitID可以通过git log查看

##### 3.5 查看已经删除的提交记录

> git reflog

##### 3.6 添加文件至忽略列表

> 创建一个名为.gitgnore的文件，列出要忽略的文件模式

> ![5d16254d-b8a2-4259-844a-bb834d33a1d6](file:///C:/Users/%E8%91%9B%E8%90%A8%E6%A1%91%E6%A1%91/Pictures/Typedown/5d16254d-b8a2-4259-844a-bb834d33a1d6.png)

##### 3.7 分支

把工作工作从开发主线上分离出来

> git branch ：查看本地分支
> 
> git branch 分支名：创建本地分支
> 
> git checkout 分支名：切换分支
> 
> git merge 分支名：合并分支
> 
> git branch -d 分支名：删除分支时做各种检查
> 
> git branch -D 分支名：不做检查强制删除

#### 4. Git远程仓库

> git remote add  远端名称 远程仓库路径
> 
> git push [--set-upstream] 远端名称 分支名称(要推送的分支名称)：远端分支名：--set-upstream表示建立起和远端分支的关联关系
> 
> git clone 远程仓库路径
> 
> git pull 云端名称 云端分支名


