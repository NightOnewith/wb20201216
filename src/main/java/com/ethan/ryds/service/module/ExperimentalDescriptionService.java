package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.ExperimentalDescription;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 实验描述表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-03
 */
public interface ExperimentalDescriptionService extends IService<ExperimentalDescription> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

}
