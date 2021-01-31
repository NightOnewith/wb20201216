package com.ethan.ryds.controller.module;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.common.utils.ViewCountUtils;
import com.ethan.ryds.entity.module.PageView;
import com.ethan.ryds.service.module.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description TODO
 * @Author Ethan
 * @Date 2020/7/1 21:41
 */
@RestController
@RequestMapping("/page/view")
public class PageViewController {

    @Autowired
    private PageViewService pageViewService;

    @RequestMapping("/index")
    public R index(ServletRequest request) {
        /**
         * 页面访问量统计
         *
         * 这里写在后端统计相对于前端统计更合理，
         * 并且oauth的拦截器会拦截所有请求，所以写在这里最合适，
         * 判断请求的url，对需要统计的url进行count+1就行。
         *
         */
        HttpServletRequest req = (HttpServletRequest) request;
        StringBuffer requestURL = req.getRequestURL();
        String[] strings = requestURL.toString().split("/");
        String targetUrl = strings[strings.length-3]+"/"+strings[strings.length-2]+"/"+strings[strings.length-1];

        if ("page/view/index".equals(targetUrl)) {
            ViewCountUtils.setUrlCount(requestURL.toString());
        }

        return R.ok();
    }

    @RequestMapping("/count")
    public R count() {
        Integer totalCount = 0;
        Integer todayCount = 0;

        // 获取总访问量
        List<PageView> list = pageViewService.list();
        for (int i = 0; i < list.size(); i++) {
            totalCount += list.get(i).getViews();
        }

        // 获取今日访问量
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = formatter.format(LocalDateTime.now());

        List<PageView> pageViews = pageViewService.list();
        for (int i = 0; i < pageViews.size(); i++) {
            if (nowDate.equals(formatter.format(pageViews.get(i).getCreateTime()))) {
                todayCount = pageViews.get(i).getViews();
            }
        }


        return R.ok().put("totalCount", totalCount + 1).put("todayCount", todayCount + 1);
    }
}
