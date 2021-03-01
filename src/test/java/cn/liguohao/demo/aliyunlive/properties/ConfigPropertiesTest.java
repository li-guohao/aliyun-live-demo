package cn.liguohao.demo.aliyunlive.properties;

import cn.liguohao.demo.properties.ConfigProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigPropertiesTest {

    @Autowired
    private ConfigProperties configProperties;

    @Test
    public void test(){
        System.out.println(configProperties.getRegionId());
    }

}
