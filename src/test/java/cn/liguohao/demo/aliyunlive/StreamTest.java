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
        System.out.println(stream.getPullStreamUrl());
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

        //带有鉴权的推流URL格式：`rtmp://DomainName/AppName/StreamName?auth_key=timestamp-rand-uid-md5hash`
        String pushStreamUrl = configPro.getProperty("pushProtocol") + "://" + configPro.getProperty("pushDomainName")
                + relativeURI +"?auth_key="+pushAuthKey;
        String pullStreamUrl = configPro.getProperty("pullProtocol") + "://" + configPro.getProperty("domainName")
                + relativeURI +"?auth_key="+pullAuthKey;

        stream.setPushStreamUrl(pushStreamUrl);
        stream.setPullStreamUrl(pullStreamUrl);
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
        private String pushStreamUrl;
        private String pullStreamUrl;

        public String getPushStreamUrl() {
            return pushStreamUrl;
        }

        public void setPushStreamUrl(String pushStreamUrl) {
            this.pushStreamUrl = pushStreamUrl;
        }

        public String getPullStreamUrl() {
            return pullStreamUrl;
        }

        public void setPullStreamUrl(String pullStreamUrl) {
            this.pullStreamUrl = pullStreamUrl;
        }

        @Override
        public String toString() {
            return "Stream{" +
                    "pushStreamUrl='" + pushStreamUrl + '\'' +
                    ", pullStreamUrl='" + pullStreamUrl + '\'' +
                    '}';
        }
    }

}
