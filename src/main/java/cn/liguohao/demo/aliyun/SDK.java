package cn.liguohao.demo.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**配置好权限的阿里云SDK客户端
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
@Component
public class SDK {

    private IAcsClient iacsclient;
    private Properties configPro ;
    private final static String userDir = System.getProperty("user.home");
    private final static String configProFileName = "config.properties";
    private final static String aliyunLiveConfigProFile = userDir+"/aliyunliveconfig/"+configProFileName;

    @PostConstruct
    public void init() throws IOException {
        // 读取 <当前用户目录>/aliyunliveconfig/config.properties 文件 基于流读取
        Properties configPro = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(aliyunLiveConfigProFile));
        configPro.load(bufferedReader);
        // 根据SDK权限key初始化 IAcsClient
        DefaultProfile profile = DefaultProfile.getProfile(configPro.getProperty("regionId"), configPro.getProperty("accessKeyId"), configPro.getProperty("accessKeySecret"));
        iacsclient = new DefaultAcsClient(profile);
    }

    /**
     * 返回IAcsClient
     * @return com.aliyuncs.IAcsClient
     */
    public IAcsClient iacsclient(){return iacsclient;}
}
