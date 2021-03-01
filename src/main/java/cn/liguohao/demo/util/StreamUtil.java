package cn.liguohao.demo.util;

import cn.liguohao.demo.entity.StreamInfo;
import org.springframework.util.DigestUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**流工具类
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
public class StreamUtil {


    private Properties configPro ;
    private final static String userDir = System.getProperty("user.home");
    private final static String configProFileName = "config.properties";
    private final static String aliyunLiveConfigProFile = userDir+"/aliyunliveconfig/"+configProFileName;

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String toMD5(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 生成推拉流URL
     * @param streamName 流名称
     * @return 流URL信息
     */
    public static StreamInfo generatePushAndPullStreamUrl(String streamName) throws IOException {

        // 读取 <当前用户目录>/aliyunliveconfig/config.properties 文件 基于流读取
        Properties configPro = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(aliyunLiveConfigProFile));
        configPro.load(bufferedReader);

        StreamInfo stream = new StreamInfo();

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
//        String pullStreamUrlRTMP = "rtmp://" + configPro.getProperty("domainName")
//                + relativeURI +"?auth_key="+pullAuthKey;
//        String pullStreamUrlFLV = "http://" + configPro.getProperty("domainName")
//                + relativeURI + ".flv?auth_key="+pullAuthKey;
//        String pullStreamUrlM3U8 = "http://" + configPro.getProperty("domainName")
//                + relativeURI + ".m3u8?auth_key="+pullAuthKey;
//        String pullStreamUrlUDP = "artc://" + configPro.getProperty("domainName")
//                + relativeURI + "?auth_key="+pullAuthKey;

        String pullStreamUrlRTMP = "rtmp://" + configPro.getProperty("domainName")
                + relativeURI ;
        String pullStreamUrlFLV = "http://" + configPro.getProperty("domainName")
                + relativeURI + ".flv";
        String pullStreamUrlM3U8 = "http://" + configPro.getProperty("domainName")
                + relativeURI + ".m3u8";
        String pullStreamUrlUDP = "artc://" + configPro.getProperty("domainName")
                + relativeURI ;


        stream.setPushStreamUrl(pushStreamUrl);
        stream.setPullStreamUrlRTMP(pullStreamUrlRTMP);
        stream.setPullStreamUrlFLV(pullStreamUrlFLV);
        stream.setPullStreamUrlM3U8(pullStreamUrlM3U8);
        stream.setPullStreamUrlUDP(pullStreamUrlUDP);
        return stream;
    }
}
