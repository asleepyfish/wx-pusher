<p align="center">
<h1 align="center">✨ wx-pusher</h1>
<div align="center">微信自定义推送</div>
</p>

> 前言：由于很多朋友咨询问题，有时候会看不到申请好友信息，回复不过来，这里创建了一个交流群，有问题群内问，这样后续加入的人有相同的问题也方便解决
# **_QQ群号：640827352_**

项目介绍地址：[https://blog.csdn.net/qq_41821963/article/details/127040795](https://blog.csdn.net/qq_41821963/article/details/127040795)
> 注：如果不需要ChatGPT功能，请直接下载release版本代码
> https://github.com/asleepyfish/wx-pusher/archive/refs/tags/1.0.0.zip

小白版介绍地址：[https://blog.csdn.net/qq_41821963/article/details/127479741](https://blog.csdn.net/qq_41821963/article/details/127479741)

ChatGPT聊天机器人体验，扫码关注：

![scan.jpg](http://alpacos.cn/images/scan.jpg)

效果：

![chat1.png](https://img-blog.csdnimg.cn/2fd967128d9d4292ab9b9f220893477e.png)

如果需要接入ChatGPT，请按照下面教程配置。
# 1.准备阶段
## 1.1 基础性配置
首先下载源码
> **==Git项目地址==:** [https://github.com/asleepyfish/wx-pusher](https://github.com/asleepyfish/wx-pusher)

其次确保你已经看过第一篇文章：
[微信公众号（一）每日推送详细教程（含实时定位，天气预报，每日英语，纪念日等，可快速自定义消息模板并指定订阅者类型发送）](https://blog.csdn.net/qq_41821963/article/details/127040795)，==按照文章内容做好基础性的配置工作==。

## 1.2 账号准备
链接：[https://platform.openai.com/account/api-keys](https://platform.openai.com/account/api-keys)。
`注意：`这里的API KEYS创建好以后一定要妥善保存，创建以后，第二次就无法再查看了，想要再看，只能删除了API KEYS然后重新创建。
![在这里插入图片描述](https://img-blog.csdnimg.cn/9fd9603fabba401f82a57f834a2ab6e4.png)
这里的API KEYS妥善保管后面会用到。
# 2. 配置阶段
## 2.1 配置application.yml文件
在`application.yml`文件中配置chatgpt相关参数（Optional为可选参数）

**注：大陆用户需要配置proxy-host和proxy-port来进行代理才能访问OpenAI服务**

| 参数                               | 解释                                                         |
| ---------------------------------- | ------------------------------------------------------------ |
| token                              | 申请的API KEYS                                               |
| proxy-host (Optional)              | 代理的ip                                                     |
| proxy-port (Optional)              | 代理的端口                                                   |
| model (Optional)                   | model可填可不填，默认即text-davinci-003                      |
| chat-model (Optional)              | 可填可不填，默认即gpt-3.5-turbo （ChatGPT当前最强模型，生成回答使用的就是这个模型） |
| retries (Optional)                 | 指的是当chatgpt第一次请求回答失败时，重新请求的次数（增加该参数的原因是因为大量访问的原因，在某一个时刻，chatgpt服务将处于无法访问的情况，不填的默认值为5） |
| session-expiration-time (Optional) | （单位（min））为这个会话在多久不访问后被销毁，这个值不填的时候，即表示所有问答处于同一个会话之下，相同user的会话永不销毁（增加请求消耗） |

例：

```yml
chatgpt:
  token: sk-xxxxxxxxxxxxxxx
  proxy-host: 127.0.0.1
  proxy-port: xxxx
  session-expiration-time: 30
```

**_其中token必填、大陆用户proxy-host、proxy-port也是必填的（某些你懂的原因）_**

需要准备好代理，我这里用的是Clash，然后需要有国外的节点，proxy-host、proxy-port即Clash的代理ip和端口

## 2.2 注解
启动类上加入下面的注解则将服务注入到Spring中。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2723c69669244b5da07c0752b0585945.png)
# 3. 部署
具体部署请参考上一篇文章[微信公众号（一）每日推送详细教程](https://blog.csdn.net/qq_41821963/article/details/127040795)中相应章节。
