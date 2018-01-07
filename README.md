# Huluwa_FinalProject
Java大作业 葫芦娃大战妖精

151220099 汤沁予

## 大致说明

### 模块分割

本次工程分为文件存取模块、屏幕显示模块以及生物模块，其中屏幕输出模块为主要模块，其中的FightField类起到控制和沟通各个模块的作用。

### 程序状态变化

程序根据不同的功能，分为“初始状态”、“战斗状态”、“回放状态”和“结束状态”。

初始状态是刚进入程序中时的状态，此时可以选择按空格进入战斗状态或是按L进入回访状态。

若选中战斗状态，程序自动运行，当正义或是邪恶一方全灭时进入结束状态。

若选中回放状态，则弹出文件选择窗口，选择需要回放的存档文件，然后自动播放。

结束之后，进入结束状态，此时还可以继续回放存档，但是不能再开始战斗。

### 关于生物对象的一些说明

* 每个生物在检测到可攻击范围内有敌人时，则会向FightField发起攻击请求，FightField会取一个0到100的随机数，根据这个生物本身持有的“战斗力”与这个随机数的大小关系来决定被攻击方是否死亡。

* 为了方便进行敌我判断并攻击，在生物模块中，将所有的生物分为了正义和邪恶两方，各个类的继承关系如下：
<img src="/Screen_Shots/Creature.JPG" width="600">

* 为了方便管理，给Creature添加了一个Thread类型的成员变量，每个生物持有自己的线程，并且在FightField中初始化这些线程，并使用了一个ArrayList来存储所有的线程。

## 实验截图

* 进入程序后，屏幕截图如下：
<img src="/Screen_Shots/initial.JPG" width="600">

* 按下空格键后，葫芦娃和妖精开始进行战斗：
<img src="/Screen_Shots/Fighting.JPG" width="600">

* 若在未开始或是结束状态下按下L键，则可以弹出文件选择框，进行记录的回放：
<img src="/Screen_Shots/SelectingFile.JPG" width="600">


# 结语

本次葫芦娃大实验，大致上完成了所要求的功能，由于时间关系，还有许多可以改进的地方。

如果老师和同学发现程序中存在的bug，欢迎与我讨论。
