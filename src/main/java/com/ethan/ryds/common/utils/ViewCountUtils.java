package com.ethan.ryds.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.entity.module.PageView;
import com.ethan.ryds.service.module.PageViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 页面访问量统计工具类
 * @Author Ethan
 * @Date 2020/7/1 20:58
 */
@Component
public class ViewCountUtils {

    @Autowired
    private PageViewService pageViewService;

    private static ViewCountUtils viewCountUtils;


    // 实例化 pageViewService 接口
    @PostConstruct
    public void init() {
        viewCountUtils = this;
        viewCountUtils.pageViewService = this.pageViewService;
    }

    // 设置需要统计的页面url访问量
    public static void setUrlCount(String url) {
        LambdaQueryWrapper<PageView> queryWrapper = new LambdaQueryWrapper<>();
        if (!url.isEmpty() && url != null) {
            queryWrapper.eq(PageView::getPageUrl, url);
        }
        queryWrapper.like(PageView::getCreateTime, todayDate());

        PageView pageView = viewCountUtils.pageViewService.getOne(queryWrapper);

        // 更新
        if (pageView != null) {
            updatePageViews(url);
        } else {
            // 新增
            savePageViews(url);
        }

    }

    // 保存访问量
    public static void savePageViews(String url) {
        PageView pageView = new PageView();
        pageView.setPageUrl(url);
        pageView.setViews(1);
        pageView.setCreateTime(LocalDateTime.now());
        viewCountUtils.pageViewService.save(pageView);
    }

    // 更新访问量
    public static void updatePageViews(String url) {
        LambdaQueryWrapper<PageView> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageView::getPageUrl, url);
        queryWrapper.like(PageView::getCreateTime, todayDate());
        PageView view = viewCountUtils.pageViewService.getOne(queryWrapper);

        view.setViews(view.getViews() + 1);
        view.setCreateTime(LocalDateTime.now());

        viewCountUtils.pageViewService.updateById(view);
    }

    // 当天日期
    public static String todayDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String today = formatter.format(date);

        return today;
    }

}
