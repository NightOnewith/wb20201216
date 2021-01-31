package com.ethan.ryds.controller.module;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.ExperimentalDescription;
import com.ethan.ryds.service.module.ExperimentalDescriptionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实验描述表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-03
 */
@RestController
@RequestMapping("/experDes")
public class ExperimentalDescriptionController {

    @Autowired
    private ExperimentalDescriptionService experimentalDescriptionService;

    @RequestMapping("/list")
    @RequiresPermissions("exper:des:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = experimentalDescriptionService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/save")
    @RequiresPermissions("exper:des:save")
    public R save(@RequestBody ExperimentalDescription experimentalDescription) {
        boolean save = false;

        // 新增的时候判断title重名
        if (experimentalDescription.getId() == null) {
            LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ExperimentalDescription::getTitle, experimentalDescription.getTitle());
            List<ExperimentalDescription> list = experimentalDescriptionService.list(queryWrapper);

            if (list.size() > 0) {
                return R.error("标题名称已存在，请修改标题！");
            }
        } else { // 更新时判断title修改的内容是否重名
            LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ExperimentalDescription::getTitle, experimentalDescription.getTitle());
            queryWrapper.notIn(ExperimentalDescription::getId, experimentalDescription.getId());
            List<ExperimentalDescription> list = experimentalDescriptionService.list(queryWrapper);

            if (list.size() > 0) {
                return R.error("标题名称已存在，请修改标题！");
            }
        }

        experimentalDescription.setCreateTime(LocalDateTime.now());
        save = experimentalDescriptionService.saveOrUpdate(experimentalDescription);

        return save ? R.ok() : R.error("保存失败");
    }

    @RequestMapping("/delete")
    @RequiresPermissions("exper:des:delete")
    public R delete(@RequestBody Integer id) {
        boolean remove = experimentalDescriptionService.removeById(id);

        return remove ? R.ok() : R.error("删除失败");
    }

    @RequestMapping("/info/{title}")
    @RequiresPermissions("exper:des:info")
    public R info(@PathVariable("title") String title) {
        LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExperimentalDescription::getTitle, title);

        ExperimentalDescription experimentalDescription = experimentalDescriptionService.getOne(queryWrapper);

        return R.ok().put("experDesc", experimentalDescription);
    }

    @RequestMapping("/descinfo/{id}")
    public R descinfo(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExperimentalDescription::getId, id);

        ExperimentalDescription experimentalDescription = experimentalDescriptionService.getOne(queryWrapper);

        return R.ok().put("experDesc", experimentalDescription);
    }

    @RequestMapping("/select")
    public R select() {
        LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.notLike(ExperimentalDescription::getTitle, "资源共享协议书");
        List<ExperimentalDescription> list = experimentalDescriptionService.list(queryWrapper);

        // 标题集合 (前端顶部菜单栏中的 “实验描述” 选项显示)
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getTitle());
        }

        return R.ok().put("list", list).put("titleList", titleList);
    }

    // 获取共享协议书
    @RequestMapping("/gxxy")
    public R selectGxxy() {
        LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ExperimentalDescription::getTitle, "资源共享协议书");
        ExperimentalDescription description = experimentalDescriptionService.getOne(queryWrapper);

        return R.ok().put("data", description);
    }

    // 获取实验项目介绍
    @RequestMapping("/syxmjs")
    public R selectSyxmjs() {
        LambdaQueryWrapper<ExperimentalDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ExperimentalDescription::getTitle, "实验项目介绍");
        ExperimentalDescription description = experimentalDescriptionService.getOne(queryWrapper);

        return R.ok().put("data", description);
    }

}

