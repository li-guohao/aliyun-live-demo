package cn.liguohao.demo.aliyunlive;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Properties;

/**直播流相关测试
 * 需要在当前用户目录下创建对应的文件夹和配置文件
 * <当前用户目录>/aliyunliveconfig/config.properties
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
public class StreamTest {

    private Properties configPro ;
    private final String userDir = System.getProperty("user.home");
    private final String configProFileName = "config.properties";
    private final String aliyunLiveConfigProFile = userDir+"/aliyunliveconfig/"+configProFileName;


    @Before
    public void init(){
        // 读取 <当前用户目录>/aliyunliveconfig/config.properties 文件 基于流读取
        configPro = new Properties();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(aliyunLiveConfigProFile));
            configPro.load(bufferedReader);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    @Test
    public void generatePushAndPullStreamUrlCoarse(){
        String streamName = "streamName=00001";
        long timestamp = System.currentTimeMillis();

        //md5hash URI-timestamp-rand-uid-PrivateKey
        String pushMdhHash = "/"+configPro.getProperty("appName")+"/"+streamName+"-"+timestamp
                +"-"+configPro.getProperty("rand")+"-"+configPro.getProperty("uid")+"-"+configPro.getProperty("pushPrivateKey");
        pushMdhHash = toMD5(pushMdhHash);
        //带有鉴权的推流URL格式：`rtmp://DomainName/AppName/StreamName?auth_key=timestamp-rand-uid-md5hash
        String pushAuthKey = timestamp +"-"+ configPro.getProperty("rand") +"-"+ configPro.getProperty("uid")
                +"-"+pushMdhHash;
        String pushStreamUrl = configPro.getProperty("pushProtocol") + "://" + configPro.getProperty("pushDomainName")
                            +"/"+configPro.getProperty("appName") +"/"+ streamName +"?auth_key="+pushAuthKey;

        System.out.println("pushStreamUrl ==> "+pushStreamUrl);


        String pullMdhHash = "/"+configPro.getProperty("appName")+"/"+streamName+"-"+timestamp
                +"-"+configPro.getProperty("rand")+"-"+configPro.getProperty("uid")+"-"+configPro.getProperty("pullPrivateKey");
        pullMdhHash = toMD5(pullMdhHash);
        String pullAuthKey = timestamp +"-"+ configPro.getProperty("rand") +"-"+ configPro.getProperty("uid")
                +"-"+pullMdhHash;
        String pullStreamUrl = configPro.getProperty("pullProtocol") + "://" + configPro.getProperty("domainName")
                +"/"+configPro.getProperty("appName") +"/"+ streamName +"?auth_key="+pullAuthKey;

        System.out.println("pullStreamUrl ==> "+pullStreamUrl);


    }

    @Test
    public void generatePushAndPullStreamUrlTest(){
        Stream stream = generatePushAndPullStreamUrl("test4Stream90903");
        System.out.println(stream.getPushStreamUrl());
        System.out.println(stream.getPullStreamUrlRTMP());
        System.out.println(stream.getPullStreamUrlFLV());
        System.out.println(stream.getPullStreamUrlM3U8());
        System.out.println(stream.getPullStreamUrlUDP());
    }

    /**
     * 生成推拉流URL
     * @param streamName 流名称
     * @return 流URL信息
     */
    private Stream generatePushAndPullStreamUrl(String streamName){
        Stream stream = new Stream();

        long timestamp = System.currentTimeMillis();
        // 如/AppName/StreamName
        String relativeURI = "/"+configPro.getProperty("appName")+"/"+streamName;
        // URI-timestamp-rand-uid
        String md5HashPrefix =  relativeURI+"-"+timestamp
                +"-"+configPro.getProperty("rand")+"-"+configPro.getProperty("uid");
        //md5hash URI-timestamp-rand-uid-PrivateKey
        String pushMd5Hash = toMD5(md5HashPrefix + "-"+configPro.getProperty("pushPrivateKey"));
        String pullMd5Hash = toMD5(md5HashPrefix + "-"+configPro.getProperty("pullPrivateKey"));
        //auth_key timestamp-rand-uid-md5hash
        String authKeyPrefix = timestamp +"-"+ configPro.getProperty("rand") +"-"+ configPro.getProperty("uid");
        String pushAuthKey = authKeyPrefix+"-"+pushMd5Hash;
        String pullAuthKey = authKeyPrefix+"-"+pullMd5Hash;

        //推流地址拼接规则 推流域名+AppName（应用）+StreamName（直播流）+鉴权串
        //rtmp://DomainName/AppName/StreamName?auth_key=timestamp-rand-uid-md5hash
        String pushStreamUrl = "rtmp://" + configPro.getProperty("pushDomainName")
                + relativeURI +"?auth_key="+pushAuthKey;
        //播流地址拼接规则 播流域名+AppName（应用）+StreamName（直播流）+鉴权串
        // 构建不同格式播流地址
        String pullStreamUrlRTMP = "rtmp://" + configPro.getProperty("domainName")
                + relativeURI +"?auth_key="+pullAuthKey;
        String pullStreamUrlFLV = "http://" + configPro.getProperty("domainName")
                + relativeURI + ".flv?auth_key="+pullAuthKey;
        String pullStreamUrlM3U8 = "http://" + configPro.getProperty("domainName")
                + relativeURI + ".m3u8?auth_key="+pullAuthKey;
        String pullStreamUrlUDP = "artc://" + configPro.getProperty("domainName")
                + relativeURI + "?auth_key="+pullAuthKey;

        stream.setPushStreamUrl(pushStreamUrl);
        stream.setPullStreamUrlRTMP(pullStreamUrlRTMP);
        stream.setPullStreamUrlFLV(pullStreamUrlFLV);
        stream.setPullStreamUrlM3U8(pullStreamUrlM3U8);
        stream.setPullStreamUrlUDP(pullStreamUrlUDP);
        return stream;
    }




    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String toMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 流信息
     */
    class Stream {
        /**
         * 推流地址
         * rtmp://push.aliyunlive.com/live/0000?auth_key={鉴权串}
         */
        private String pushStreamUrl;
        /**
         * RTMP格式播流地址
         * rtmp://pull.aliyunlive.com/live/0000?auth_key={鉴权串}
         */
        private String pullStreamUrlRTMP;
        /**
         * FLV格式播流地址
         * http://pull.aliyunlive.com/live/0000.flv?auth_key={鉴权串}
         */
        private String pullStreamUrlFLV;
        /**
         * M3U8格式播流地址
         * http://pull.aliyunlive.com/live/0000.m3u8?auth_key={鉴权串}
         */
        private String pullStreamUrlM3U8;
        /**
         * UDP格式播流地址
         * artc://pull.aliyunlive.com/live/0000?auth_key={鉴权串}
         */
        private String pullStreamUrlUDP;


        public String getPushStreamUrl() {
            return pushStreamUrl;
        }

        public void setPushStreamUrl(String pushStreamUrl) {
            this.pushStreamUrl = pushStreamUrl;
        }

        public String getPullStreamUrlRTMP() {
            return pullStreamUrlRTMP;
        }

        public void setPullStreamUrlRTMP(String pullStreamUrlRTMP) {
            this.pullStreamUrlRTMP = pullStreamUrlRTMP;
        }

        public String getPullStreamUrlFLV() {
            return pullStreamUrlFLV;
        }

        public void setPullStreamUrlFLV(String pullStreamUrlFLV) {
            this.pullStreamUrlFLV = pullStreamUrlFLV;
        }

        public String getPullStreamUrlM3U8() {
            return pullStreamUrlM3U8;
        }

        public void setPullStreamUrlM3U8(String pullStreamUrlM3U8) {
            this.pullStreamUrlM3U8 = pullStreamUrlM3U8;
        }

        public String getPullStreamUrlUDP() {
            return pullStreamUrlUDP;
        }

        public void setPullStreamUrlUDP(String pullStreamUrlUDP) {
            this.pullStreamUrlUDP = pullStreamUrlUDP;
        }
    }

}
