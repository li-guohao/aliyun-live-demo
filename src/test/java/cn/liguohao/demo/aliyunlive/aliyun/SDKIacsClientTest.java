package cn.liguohao.demo.aliyunlive.aliyun;

import cn.liguohao.demo.aliyun.SDK;
import cn.liguohao.demo.properties.ConfigProperties;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.live.model.v20161101.DescribeLiveStreamsOnlineListRequest;
import com.aliyuncs.live.model.v20161101.DescribeLiveStreamsOnlineListResponse;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SDKIacsClientTest {

    @Autowired
    private SDK sdk;
    @Autowired
    private ConfigProperties configProperties;

    private IAcsClient client;

    @Before
    public void init(){
        client = sdk.iacsclient();
    }

    /**
     * 查询在线域名测试
     */
    @Test
    public void seachOnlineLive(){
        DescribeLiveStreamsOnlineListRequest request = new DescribeLiveStreamsOnlineListRequest();
        request.setDomainName(configProperties.getDomainName());

        try {
            DescribeLiveStreamsOnlineListResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

    }


}
