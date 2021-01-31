package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.entity.module.PageView;
import com.ethan.ryds.dao.module.PageViewMapper;
import com.ethan.ryds.service.module.PageViewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 网站访问量 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
@Service
public class PageViewServiceImpl extends ServiceImpl<PageViewMapper, PageView> implements PageViewService {

}
