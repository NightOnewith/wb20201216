package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.entity.module.QuestionTest;
import com.ethan.ryds.entity.module.StudentScore;
import com.ethan.ryds.service.module.QuestionTestService;
import com.ethan.ryds.service.module.StudentScoreService;
import com.ethan.ryds.service.sys.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 测试题表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/questionTest")
public class QuestionTestController extends AbstractController {

    @Autowired
    private QuestionTestService questionTestService;

    @Autowired
    private StudentScoreService studentScoreService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/list")
    @RequiresPermissions("question:test:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = questionTestService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/select")
    public R select() {
        List<QuestionTest> list = questionTestService.selectQuestions();

        Integer totalScore = 0;
        for (int i = 0; i < list.size(); i++) {
            totalScore += list.get(i).getScore();
        }

        return R.ok().put("list", list).put("totalScore", totalScore).put("size", list.size());
    }

    @GetMapping("/info/{id}")
    @RequiresPermissions("question:test:info")
    public R userInfo(@PathVariable("id") Integer id){
        QuestionTest questionTest = questionTestService.getById(id);

        return R.ok().put("questionTest", questionTest);
    }

    @RequestMapping("/save")
    @RequiresPermissions("question:test:save")
    public R save(@RequestBody QuestionTest questionTest) {
        int i = questionTestService.saveQuestion(questionTest);

        return i > 0 ? R.ok("添加成功") : R.error("添加失败");
    }

    @PostMapping("/update")
    @RequiresPermissions("question:test:update")
    public R update(@RequestBody QuestionTest questionTest){
        questionTest.setType(1);
        questionTest.setCreateTime(LocalDateTime.now());
        boolean update = questionTestService.updateById(questionTest);

        return update ? R.ok("更新成功") : R.error("更新失败");
    }

    @PostMapping("/delete")
    @RequiresPermissions("question:test:delete")
    public R delete(@RequestBody Integer id){

        return questionTestService.removeById(id) ? R.ok() : R.error("删除失败");
    }

    @PostMapping("/submit")
    public R submit(@RequestBody Map<String, Object> params) {
        // 试卷总分
        int totalScore = (int) params.get("totalScore");
        // 学生测试分数
        int testScore = studentScoreService.saveTestScore(params);
        if (testScore == -1) {
            return R.error("题目未答完！");
        }

        // 保存到数据库
        StudentScore studentScore = new StudentScore();
        studentScore.setScore(testScore);
        studentScore.setTotalScore(totalScore);
        studentScore.setProjectName("自测题");
        studentScore.setCreateTime(LocalDateTime.now());

        if (params.get("userId") != null && !params.get("userId").equals("")) {
            studentScore.setAccount(sysUserService.getById(Integer.valueOf(params.get("userId").toString())).getUsername());
        } else if (params.get("account") != null && !params.get("account").equals("")) {
            studentScore.setAccount(params.get("account").toString());
        }

        boolean save = studentScoreService.save(studentScore);

        return save ? R.ok().put("totalScore", totalScore).put("testScore", testScore) : R.error("分数保存失败！");
    }

}

