package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.QuestionTest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试题表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-07
 */
public interface QuestionTestService extends IService<QuestionTest> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存
     */
    int saveQuestion(QuestionTest questionTest);

    /**
     * 前端测试题查询
     */
    List<QuestionTest> selectQuestions();

    /**
     * 查询所有问题的正确答案
     */
    List<Integer> selectAllQuestionsAnswer();

    /**
     * 查询所有问题的分数
     */
    List<Integer> selectAllQuestionsScore();

}
