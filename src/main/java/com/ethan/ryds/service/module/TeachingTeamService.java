package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.TeachingTeam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 教学团队表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-04
 */
public interface TeachingTeamService extends IService<TeachingTeam> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

}
