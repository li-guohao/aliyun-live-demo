package cn.liguohao.demo.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**MD5工具类
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/3/1
 */
public class MD5Util {

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String encrypt(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
}
