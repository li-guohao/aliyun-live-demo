package cn.liguohao.demo.controller;

import cn.liguohao.demo.entity.StreamInfo;
import cn.liguohao.demo.util.StreamUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**直播
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
@Controller
@RequestMapping("/live")
public class LiveController {

    @PostMapping("/open")
    public String openLive(Model model, String streamName) {
        try {
            StreamInfo streamInfo = StreamUtil.generatePushAndPullStreamUrl(streamName==null?"null":streamName.toString());
            model.addAttribute("streamInfo",streamInfo);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            model.addAttribute("message",ioException.getMessage());
        }
        return "liveInfo";
    }

    @PostMapping("/play")
    public String play(Model model, String m3u8url){
        model.addAttribute("m3u8url",m3u8url);
        return "play";
    }


}
