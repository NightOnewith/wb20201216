package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.EvaluateInfo;
import com.ethan.ryds.service.module.EvaluateInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 评价信息表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@RestController
@RequestMapping("/evaluateInfo")
public class EvaluateInfoController {

    @Autowired
    private EvaluateInfoService evaluateInfoService;

    @RequestMapping("/list")
    // @RequiresPermissions("evaluate:info:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = evaluateInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/delete")
    @RequiresPermissions("evaluate:info:delete")
    public R delete(@RequestBody Integer id) {
        boolean remove = evaluateInfoService.removeById(id);

        return remove ? R.ok() : R.error("删除失败");
    }

    @RequestMapping("/save")
    public R save(@RequestBody EvaluateInfo evaluateInfo) {
        evaluateInfo.setCreateTime(LocalDateTime.now());
        boolean save = evaluateInfoService.save(evaluateInfo);

        return save ? R.ok() : R.error("评价失败");
    }

}

