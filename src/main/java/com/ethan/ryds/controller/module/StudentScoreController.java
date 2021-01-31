package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.POIExcelUtil;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.StudentScore;
import com.ethan.ryds.service.module.StudentScoreService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 学生实验成绩表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-06
 */
@RestController
@RequestMapping("/studentScore")
public class StudentScoreController {

    @Autowired
    private StudentScoreService studentScoreService;

    @RequestMapping("/list")
    @RequiresPermissions("student:score:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = studentScoreService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 导出为Excel
     */
    @RequestMapping(method = RequestMethod.GET, value = "/export", produces ="application/json;charset=UTF-8")
    @ResponseBody
    @RequiresPermissions("student:score:export")
    public void exportToExcel(HttpServletResponse response) {
        List<StudentScore> list = studentScoreService.list();
        for (int i = 0; i < list.size(); i++) {
            StudentScore studentScore = list.get(i);
            LocalDateTime createTime = studentScore.getCreateTime();
        }

        //  输出文件流
        POIExcelUtil.exportExcel(list, "学生成绩表", "学生成绩", StudentScore.class, "学生成绩表.xls", true, response);

    }

}

