package com.ethan.ryds.controller.module;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.TeachingTeam;
import com.ethan.ryds.service.module.TeachingTeamService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教学团队表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/teachingTeam")
public class TeachingTeamController {

    @Autowired
    private TeachingTeamService teachingTeamService;

    @RequestMapping("/list")
    @RequiresPermissions("teaching:team:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = teachingTeamService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/save")
    @RequiresPermissions("teaching:team:save")
    public R save(@RequestBody TeachingTeam teachingTeam) {
        boolean save = false;

        // 新增的时候判断title重名
        if (teachingTeam.getId() == null) {
            LambdaQueryWrapper<TeachingTeam> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TeachingTeam::getTitle, teachingTeam.getTitle());
            List<TeachingTeam> list = teachingTeamService.list(queryWrapper);

            if (list.size() > 0) {
                return R.error("教师姓名已存在，请修改教师姓名！");
            }
        }

        teachingTeam.setCreateTime(LocalDateTime.now());
        save = teachingTeamService.saveOrUpdate(teachingTeam);

        return save ? R.ok() : R.error("保存失败");
    }

    @RequestMapping("/delete")
    @RequiresPermissions("teaching:team:delete")
    public R delete(@RequestBody Integer id) {
        boolean remove = teachingTeamService.removeById(id);

        return remove ? R.ok() : R.error("删除失败");
    }

    @RequestMapping("/info/{title}")
    @RequiresPermissions("teaching:team:info")
    public R info(@PathVariable("title") String title) {
        LambdaQueryWrapper<TeachingTeam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachingTeam::getTitle, title);

        TeachingTeam teachingTeam = teachingTeamService.getOne(queryWrapper);

        return R.ok().put("teachingTeam", teachingTeam);
    }

    @RequestMapping("/select")
    public R select() {
        List<TeachingTeam> list = teachingTeamService.list();

        return R.ok().put("list", list);
    }

    @RequestMapping("/teacherinfo/{id}")
    public R teacherinfo(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<TeachingTeam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachingTeam::getId, id);

        TeachingTeam teachingTeam = teachingTeamService.getOne(queryWrapper);

        return R.ok().put("teachingTeam", teachingTeam);
    }

}

