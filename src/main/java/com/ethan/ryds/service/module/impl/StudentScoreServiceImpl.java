package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.StudentScore;
import com.ethan.ryds.dao.module.StudentScoreMapper;
import com.ethan.ryds.service.module.QuestionTestService;
import com.ethan.ryds.service.module.StudentScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生实验成绩表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-06
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<StudentScoreMapper, StudentScore> implements StudentScoreService {

    @Autowired
    private QuestionTestService questionTestService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String account = (String) params.get("account");
        String type = (String) params.get("type");
        String projectName = null;
        if ("实验".equals(type)) {
            projectName = "实验项目";
        } else if ("自测题".equals(type)) {
            projectName = "自测题";
        }

        IPage<StudentScore> page = this.page(
                new Query<StudentScore>().getPage(params),
                new QueryWrapper<StudentScore>().lambda()
                        .eq(StringUtils.isNotBlank(type), StudentScore::getProjectName, projectName)
                        .like(StringUtils.isNotBlank(account), StudentScore::getAccount, account)
                        .orderByDesc(StudentScore::getId)
        );

        return new PageUtils(page);
    }

    @Override
    public int saveTestScore(Map<String, Object> params) {
        // 问题正确答案集合
        List<Integer> realAnswers = questionTestService.selectAllQuestionsAnswer();
        // 问题分数集合
        List<Integer> scores = questionTestService.selectAllQuestionsScore();
        // 提交答案集合
        List<Map<String, Integer>> selectVal = (List<Map<String, Integer>>) params.get("selectVal");
        // 提交答案集合转换
        List<Integer> testAnswers = new ArrayList<>();
        // 学生测试分数
        Integer testScore = 0;

        for (int i = 0; i < selectVal.size(); i++) {
            Map<String, Integer> val = selectVal.get(i);
            // 学生提交的每项的答案选择 1,2,3,4   0：为未答题
            Integer selectedAnswer = val.get("value");

            // 该题目未答
            if (selectedAnswer == 0) {
                return -1;
            }

            testAnswers.add(selectedAnswer);
        }

        // 题目没有答完
        if (realAnswers.size() != testAnswers.size()) {
            return -1;
        } else {
            for (int i = 0; i < realAnswers.size(); i++) {
                // 评分
                if (realAnswers.get(i) == testAnswers.get(i)) {
                    testScore += scores.get(i);
                }
            }
        }

        return testScore;
    }

}
