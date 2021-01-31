package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.ExperimentalDescription;
import com.ethan.ryds.dao.module.ExperimentalDescriptionMapper;
import com.ethan.ryds.entity.sys.SysLog;
import com.ethan.ryds.service.module.ExperimentalDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 实验描述表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-03
 */
@Service
public class ExperimentalDescriptionServiceImpl extends ServiceImpl<ExperimentalDescriptionMapper, ExperimentalDescription> implements ExperimentalDescriptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");

        IPage<ExperimentalDescription> page = this.page(
                new Query<ExperimentalDescription>().getPage(params),
                new QueryWrapper<ExperimentalDescription>().lambda()
                        .like(StringUtils.isNotBlank(title), ExperimentalDescription::getTitle, title)
                        .orderByDesc(ExperimentalDescription::getId)
        );

        return new PageUtils(page);
    }

}
