package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.StudentScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 学生实验成绩表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-06
 */
public interface StudentScoreService extends IService<StudentScore> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存自测题分数
     */
    int saveTestScore(Map<String, Object> params);

}
