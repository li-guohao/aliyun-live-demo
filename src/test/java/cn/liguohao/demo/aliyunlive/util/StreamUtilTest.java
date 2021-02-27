package cn.liguohao.demo.aliyunlive.util;

import cn.liguohao.demo.entity.StreamInfo;
import cn.liguohao.demo.util.StreamUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.io.IOException;

/**
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
public class StreamUtilTest {

    @Test
    public void generatePushAndPullStreamUrl() throws IOException {
        StreamInfo stream = StreamUtil.generatePushAndPullStreamUrl("test001234");
        System.out.println(JSON.toJSON(stream));
    }
}
