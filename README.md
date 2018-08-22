![](http://dl.iteye.com/upload/picture/pic/137631/7a0f9e40-51e7-36c8-80a5-f947acf80aae.png)

[![License](https://img.shields.io/badge/%20license-GNU%20General%20Public%20License%20v3.0-blue.svg)](http://www.gnu.org/licenses/gpl-3.0.en.html) ![](https://img.shields.io/badge/%20build-passing-brightgreen.svg) [![Join the chat at https://gitter.im/gaoxianglong/shark](https://badges.gitter.im/gaoxianglong/shark.svg)](https://gitter.im/gaoxianglong/shark?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

云集全链路压测军演系统具备强大的分布式压测能力，**能够在短时间内发起超大规模的压测流量**，使用TITAN能够快速挖掘出业务系统的性能瓶颈，探测出业务系统的真实容量水位，有指导的在大促前进行容量规划和性能优化，让系统坚如磐石。

- [InfoQ架构文稿 & 线上全链路压测方案](http://www.infoq.com/cn/articles/yunjiweidian-12.12)<br>
- [中文使用手册](https://github.com/yunjiweidian/TITAN/wiki/%E4%B8%AD%E6%96%87%E4%BD%BF%E7%94%A8%E6%89%8B%E5%86%8C)<br>
- [安装部署手册](https://github.com/yunjiweidian/TITAN/wiki/%E5%AE%89%E8%A3%85%E9%83%A8%E7%BD%B2%E6%89%8B%E5%86%8C)<br>

----------

## 功能特点

- **天生为分布式系统而生**，具备超高的并发压测能力，支持对Agent（压测引擎）节点的无限水平扩容；
- 操作极其简单，上手快速，并且具备友好的交互式体验；
- 能够适配任何复杂的业务场景，**支持多链路组装压测，无惧任何业务场景挑战**；
- 支持对压测引擎、目标机器的可视化CPU、内存、磁盘IOPS等监控，让问题浮出水面；
- 支持定时自动化压测任务，更好的**实现线上压测常态化**；
- 便捷的压测引擎管理，无需运维手工介入（启/停）；
- 永久开源、**不阉割功能**，并且保证和云集内部版本保持一致，持续更新维护中；
- 完全采用Java语言编写，方便二次开发实现功能扩展。

----------

## 功能界面
![](http://dl.iteye.com/upload/picture/pic/137641/754bed99-683b-3978-b614-1be71443441c.png)

----------

## 整体架构
TITAN整体由如下5部分子系统构成：
- **Manager**：管理控制台，负责链路、场景等相关信息管理，以及获取压测的业务指标数据与监控指标数据；
- **TaskService**：负责具体的压测任务编排工作，并将压测任务信息下发给空闲Agent；
- **Agent**：压测引擎，向ZK注册心跳、获取压测任务并执行；
- **Monitor**：负责收集压测引擎、目标机器的CPU、内存、磁盘IOPS等监控指标数据；
- **DataCollect**：负责收集压测数据并实施上报。

![](http://dl.iteye.com/upload/picture/pic/137639/7880c09c-3490-3358-a4e4-dab919854624.jpg)

----------

## 文档&协作
- 文档地址：https://github.com/yunjiweidian/TITAN/wiki
- 协作开发：请在master分支上提pull request
- 提问题：https://github.com/yunjiweidian/TITAN/issues

----------
 
 ## 联系我们
 
- issues：https://github.com/yunjiweidian/TITAN/issues
- email：gaoxl@yunjiweidian.com
- QQ Group：574295338
- Power by云集研发中心基础架构组

----------
