package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.EvaluateInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评价信息表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
public interface EvaluateInfoService extends IService<EvaluateInfo> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

}
