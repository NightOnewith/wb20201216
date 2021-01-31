package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.EvaluateInfo;
import com.ethan.ryds.dao.module.EvaluateInfoMapper;
import com.ethan.ryds.service.module.EvaluateInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 评价信息表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@Service
public class EvaluateInfoServiceImpl extends ServiceImpl<EvaluateInfoMapper, EvaluateInfo> implements EvaluateInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String score = (String) params.get("score");
        String content = (String) params.get("content");

        LambdaQueryWrapper<EvaluateInfo> queryWrapper = new QueryWrapper<EvaluateInfo>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(content), EvaluateInfo::getContent, content);
        queryWrapper.orderByDesc(EvaluateInfo::getId);
        if (isNumeric(score)) {
            queryWrapper.eq(EvaluateInfo::getScore, Integer.valueOf(score));
        }

        IPage<EvaluateInfo> page = this.page(
                new Query<EvaluateInfo>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    public final static boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

}
