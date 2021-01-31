package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.ExperimentalDescription;
import com.ethan.ryds.entity.module.TeachingTeam;
import com.ethan.ryds.dao.module.TeachingTeamMapper;
import com.ethan.ryds.service.module.TeachingTeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 教学团队表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-04
 */
@Service
public class TeachingTeamServiceImpl extends ServiceImpl<TeachingTeamMapper, TeachingTeam> implements TeachingTeamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");

        IPage<TeachingTeam> page = this.page(
                new Query<TeachingTeam>().getPage(params),
                new QueryWrapper<TeachingTeam>().lambda()
                        .like(StringUtils.isNotBlank(title), TeachingTeam::getTitle, title)
                        /*.orderByDesc(TeachingTeam::getId)*/
        );

        return new PageUtils(page);
    }

}
