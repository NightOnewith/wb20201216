package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.Classmate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 班级信息 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-30
 */
public interface ClassmateService extends IService<Classmate> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 删除班级
     */
    int deleteByClassId(Integer classId);

}
