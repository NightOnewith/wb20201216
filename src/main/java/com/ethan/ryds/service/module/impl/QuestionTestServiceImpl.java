package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.QuestionTest;
import com.ethan.ryds.dao.module.QuestionTestMapper;
import com.ethan.ryds.service.module.QuestionTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试题表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-07
 */
@Service
public class QuestionTestServiceImpl extends ServiceImpl<QuestionTestMapper, QuestionTest> implements QuestionTestService {

    @Autowired
    private QuestionTestMapper questionTestMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String question = (String) params.get("question");

        IPage<QuestionTest> page = this.page(
                new Query<QuestionTest>().getPage(params),
                new QueryWrapper<QuestionTest>().lambda()
                        .like(StringUtils.isNotBlank(question), QuestionTest::getQuestionContent, question)
                        .orderByDesc(QuestionTest::getId)
        );

        return new PageUtils(page);
    }

    @Override
    public int saveQuestion(QuestionTest questionTest) {
        questionTest.setType(1);
        questionTest.setCreateTime(LocalDateTime.now());

        int insert = questionTestMapper.insert(questionTest);

        return insert;
    }

    @Override
    public List<QuestionTest> selectQuestions() {
        List<QuestionTest> questionTests = questionTestMapper.selectList(null);

        for (int i = 0; i < questionTests.size(); i++) {
            QuestionTest questionTest = questionTests.get(i);

            List<String> options = new ArrayList<>();
            if (StringUtils.isNotBlank(questionTest.getOptionA())) {
                options.add(questionTest.getOptionA());
            }
            if (StringUtils.isNotBlank(questionTest.getOptionB())) {
                options.add(questionTest.getOptionB());
            }
            if (StringUtils.isNotBlank(questionTest.getOptionC())) {
                options.add(questionTest.getOptionC());
            }
            if (StringUtils.isNotBlank(questionTest.getOptionD())) {
                options.add(questionTest.getOptionD());
            }

            questionTest.setOptionList(options);
        }

        return questionTests;
    }

    @Override
    public List<Integer> selectAllQuestionsAnswer() {
        List<QuestionTest> questionTests = questionTestMapper.selectList(null);

        List<Integer> answers = new ArrayList<>();
        for (int i = 0; i < questionTests.size(); i++) {
            answers.add(questionTests.get(i).getAnswer());
        }

        return answers;
    }

    @Override
    public List<Integer> selectAllQuestionsScore() {
        List<QuestionTest> questionTests = questionTestMapper.selectList(null);

        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < questionTests.size(); i++) {
            scores.add(questionTests.get(i).getScore());
        }

        return scores;
    }

}
