package cn.liguohao.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**项目阿里云相关的敏感性配置文件
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
@Component
@Configuration
@ConfigurationProperties
@PropertySource("file:${user.home}/aliyunliveconfig/config.properties")
public class ConfigProperties {

    /**
     * 地域
     */
    private String regionId;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 播流域名
     */
    private String domainName;
    /**
     * 播流URL鉴权Key
     */
    private String pullPrivateKey;
    /**
     * 推流域名
     */
    private String pushDomainName;
    /**
     * 推流URL鉴权Key，推流
     */
    private String pushPrivateKey;
    /**
     * 直播流拼接需要
     */
    private String rand;
    /**
     * 直播流拼接需要
     */
    private String uid;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getPullPrivateKey() {
        return pullPrivateKey;
    }

    public void setPullPrivateKey(String pullPrivateKey) {
        this.pullPrivateKey = pullPrivateKey;
    }

    public String getPushDomainName() {
        return pushDomainName;
    }

    public void setPushDomainName(String pushDomainName) {
        this.pushDomainName = pushDomainName;
    }

    public String getPushPrivateKey() {
        return pushPrivateKey;
    }

    public void setPushPrivateKey(String pushPrivateKey) {
        this.pushPrivateKey = pushPrivateKey;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
