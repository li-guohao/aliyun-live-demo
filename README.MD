# 介绍
一个有关阿里云视频直播的Demo
基于SpringBoot + H2 + Thymeleaf


# 直播推流拉流的坑
在一开始的时候，我按照[官方文档快速开始部分](https://help.aliyun.com/document_detail/198676.html?spm=a2c4g.11186623.6.573.30865f12Vr10W5),
使用 `视频直播` => `直播控制台` => `直播管理` => `地址生成器` 进行了试验，基本上很顺利

但当我尝试使用JAVA代码调用的时候，
我以为推流拉流都是需要需要调用一个接口打开一个长连接，可是在官方的相关文档里找了半天都找不到对应的接口，那个批量构建推流URL的接口也没有找到
在阿里官方的售后群里询问，
好家伙，群里**根本没有技术**，全是销售，我问的问题一开始谁也不敢回答，后面开始回答又打太极推来推去，回答了又避重就轻答非所问，最后自己不懂，说是要去询问下他们的PD询问，让我等消息，后面就一直没了消息。
阿里云的售后服务 **就这**？

后面经过我的多次试验(尼玛一个下午时间就没了)，我总算整明白了，原来只要配置好推流播流域名，按照一定的地址构建规则和URL鉴权规则，就能直接开始推流。
晚上回去复盘了一下，一方面项目比较紧急，导致自己有些浮躁，文档没仔细去理解，另一方面可能是因为领导一直在吹`阿里大能`,想着既然是`大能`,我想着既然能直接问，文档就没看的太仔细了。
不过复盘的时候，我也一直在心里吐槽，阿里就这？要是问题一开始有个懂技术的人在群里点醒我一些，我也不至于浪费一个下午时间在这上面纠结了，如果售后不能解决疑问，那还要售后群干啥，干脆所有的技术产品都别要售后了。

# 推流播流URL构建说明
此部分参考官方文档: 
- [推流地址和播流地址](https://help.aliyun.com/document_detail/199339.htm?spm=a2c4g.11186623.2.9.688133e3pDhrrk#concept-2010579)
- [URL鉴权](https://help.aliyun.com/document_detail/199349.htm?spm=a2c4g.11186623.2.4.30865f12CQJxON#concept-2011551)

## 地址构建
这部分挺简单的，安装[推流地址和播流地址](https://help.aliyun.com/document_detail/199339.htm?spm=a2c4g.11186623.2.9.688133e3pDhrrk#concept-2010579)
文档的规则拼接就OK
- 推流地址（鉴权）：推流域名+Appname（应用）+StreamName（直播流）+鉴权串
- 播放地址（鉴权）：播流域名+Appname（应用）+StreamName（直播流）+鉴权串
- 转码流地址（鉴权）：播流域名+Appname（应用）+StreamName（直播流）+_+转码模板ID+鉴权串
- 推流地址（未鉴权）：推流域名+Appname（应用）+StreamName（直播流）
- 播放地址（未鉴权）：播流域名+Appname（应用）+StreamName（直播流）
- 转码流地址（未鉴权）：播流域名+Appname（应用）+StreamName（直播流）+_+转码模板ID

## URL鉴权
带有鉴权的推流URL格式：`rtmp://DomainName/AppName/StreamName?auth_key=timestamp-rand-uid-md5hash`
- timestamp: 时间戳
- rand: 随机数，一般为0。不为零则建议为UUID格式，如：`477b3bbc253f467b8def6711128c7bec`
- uid: 附加参数，暂未使用，一般为0。
- md5hash: 如下
    - 格式：`URI-timestamp-rand-uid-PrivateKey` 经MD5加密后的32位字符串
    - URL：请求文件的相对地址，不包含参数，如/AppName/StreamName。
    - timestamp: 时间戳, 建议推流拉流地址同时生成，保证时间戳一致
    - rand: 随机数，推拉流好像需要一致
    - uid: 附件参数，推拉流好像需要一致
    - PrivateKey：控制台配置好的主KEY或者副KEY，二者都可，需要注意的是，推流和拉流的PrivateKey不能一致
    