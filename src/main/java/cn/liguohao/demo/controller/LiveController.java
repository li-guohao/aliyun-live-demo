package cn.liguohao.demo.controller;

import cn.liguohao.demo.entity.StreamInfo;
import cn.liguohao.demo.service.LiveStreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**直播
 * @author <a href="mailto:liguohao_cn@qq.com">liguohao_cn@qq.com</a>
 * @since 2021/2/27
 */
@Controller
@RequestMapping("/live")
public class LiveController {

    @Autowired
    private LiveStreamService liveStreamService;

    @PostMapping("/open")
    public String openLive(Model model,String appName, String streamName) {
        StreamInfo streamInfo = liveStreamService.generateStreamInfo(appName,streamName);
        model.addAttribute("streamInfo",streamInfo);
        return "liveInfo";
    }

    @PostMapping("/play")
    public String play(Model model, String m3u8url){
        model.addAttribute("m3u8url",m3u8url);
        return "play";
    }


}
