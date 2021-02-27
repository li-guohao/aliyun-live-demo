package cn.liguohao.demo.entity;

/**流信息
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
public class StreamInfo {


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
