package cn.liguohao.demo.service;

import cn.liguohao.demo.entity.StreamInfo;
import cn.liguohao.demo.properties.ConfigProperties;
import cn.liguohao.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**直播流服务类
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
@Service
public class LiveStreamService {

    @Autowired
    private ConfigProperties configProperties;



    /**
     * 生成推拉流URL
     * @param appName 播放appName（同推流appName)
     * @param streamName 播放streamName (同推流streamName）
     * @return cn.liguohao.demo.entity.StreamInfo
     */
    public StreamInfo generateStreamInfo(String appName, String streamName){
        StreamInfo stream = new StreamInfo();

        long timestamp = System.currentTimeMillis()/1000L;
        //推流地址拼接规则 推流域名+AppName（应用）+StreamName（直播流）+鉴权串

        //rtmp://DomainName/AppName/StreamName?auth_key=timestamp-rand-uid-md5hash
        String pushStreamUrl = "rtmp://" + configProperties.getPushDomainName()
                + "/" +appName +"/"+streamName;
        //配置了推流URL鉴权KEY
        if(!StringUtils.isEmpty(configProperties.getPushPrivateKey())){
            //md5hash URI-timestamp-rand-uid-PrivateKey 播流的URI这里不同格式不同
            String pushMd5Hash = MD5Util.encrypt(
                    "/" +appName +"/"+streamName +"-"+Long.toString(timestamp)
                            +"-"+configProperties.getRand()+"-"+configProperties.getUid()
                            +"-"+configProperties.getPushPrivateKey()
            );
            //auth_key timestamp-rand-uid-md5hash
            pushStreamUrl += "?auth_key="+Long.toString(timestamp)
                    +"-"+ configProperties.getRand()
                    +"-"+ configProperties.getUid()
                    +"-"+ pushMd5Hash;
        }
        //播流地址拼接规则 播流域名+AppName（应用）+StreamName（直播流）+鉴权串
        // 构建不同格式播流地址

        String pullStreamUrlRTMP = "rtmp://" + configProperties.getDomainName()
                + "/" +appName +"/"+streamName ;
        String pullStreamUrlFLV = "http://" + configProperties.getDomainName()
                + "/" +appName +"/"+streamName + ".flv";
        String pullStreamUrlM3U8 = "http://" + configProperties.getDomainName()
                + "/" +appName +"/"+streamName + ".m3u8";
        String pullStreamUrlUDP = "artc://" + configProperties.getDomainName()
                + "/" +appName +"/"+streamName ;
        //配置了播流URL鉴权KEY
        if(!StringUtils.isEmpty(configProperties.getPullPrivateKey())){
            //md5hash URI-timestamp-rand-uid-PrivateKey 播流的URI这里不同格式不同
            // RMTP格式播流
            String rmtpPullMd5Hash = MD5Util.encrypt(
                    "/" +appName +"/"+streamName +"-"+Long.toString(timestamp)
                            +"-"+configProperties.getRand()+"-"+configProperties.getUid()
                            +"-"+configProperties.getPullPrivateKey()
            );
            pullStreamUrlRTMP += "?auth_key="+Long.toString(timestamp)
                    +"-"+ configProperties.getRand()
                    +"-"+ configProperties.getUid()
                    +"-"+ rmtpPullMd5Hash;
            // FLV 格式播流
            String flvPullMd5Hash = MD5Util.encrypt(
                    "/" +appName +"/"+streamName +".flv-"+Long.toString(timestamp)
                            +"-"+configProperties.getRand()+"-"+configProperties.getUid()
                            +"-"+configProperties.getPullPrivateKey()
            );
            pullStreamUrlFLV += ".flv?auth_key="+Long.toString(timestamp)
                    +"-"+ configProperties.getRand()
                    +"-"+ configProperties.getUid()
                    +"-"+ flvPullMd5Hash;
            // M3U8格式播流
            String m3u8PullMd5Hash = MD5Util.encrypt(
                    "/" +appName +"/"+streamName +".m3u8-"+Long.toString(timestamp)
                            +"-"+configProperties.getRand()+"-"+configProperties.getUid()
                            +"-"+configProperties.getPullPrivateKey()
            );
            pullStreamUrlM3U8 += ".m3u8?auth_key="+Long.toString(timestamp)
                    +"-"+ configProperties.getRand()
                    +"-"+ configProperties.getUid()
                    +"-"+ m3u8PullMd5Hash;
            // udp格式
            String udpPullMd5Hash = MD5Util.encrypt(
                    "/" +appName +"/"+streamName +"-"+Long.toString(timestamp)
                            +"-"+configProperties.getRand()+"-"+configProperties.getUid()
                            +"-"+configProperties.getPullPrivateKey()
            );
            pullStreamUrlUDP += "?auth_key="+Long.toString(timestamp)
                    +"-"+ configProperties.getRand()
                    +"-"+ configProperties.getUid()
                    +"-"+ udpPullMd5Hash;
        }

        stream.setPushStreamUrl(pushStreamUrl);
        stream.setPullStreamUrlRTMP(pullStreamUrlRTMP);
        stream.setPullStreamUrlFLV(pullStreamUrlFLV);
        stream.setPullStreamUrlM3U8(pullStreamUrlM3U8);
        stream.setPullStreamUrlUDP(pullStreamUrlUDP);
        return stream;
    }

}
