package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dto.ClassmateDto;
import com.ethan.ryds.entity.module.Classmate;
import com.ethan.ryds.service.module.ClassmateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班级信息 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-06-30
 */
@RestController
@RequestMapping("/classmate")
public class ClassmateController extends AbstractController {

    @Autowired
    private ClassmateService classmateService;

    @RequestMapping("/list")
    @RequiresPermissions("module:classmate:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = classmateService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/select")
    @RequiresPermissions("module:classmate:list")
    public R select() {
        List<Classmate> classmates = classmateService.list();
        ArrayList<ClassmateDto> classmateDtos = new ArrayList<>();
        for (int i = 0; i < classmates.size(); i++) {
            ClassmateDto dto = new ClassmateDto();
            dto.setName(classmates.get(i).getClassname());
            dto.setLabel(classmates.get(i).getClassId().toString());

            classmateDtos.add(dto);
        }

        return R.ok().put("list", classmateDtos);
    }

    @RequestMapping("/save")
    @RequiresPermissions("module:classmate:save")
    public R save(@RequestBody Classmate classmate) {
        classmate.setCreateTime(LocalDateTime.now());
        classmateService.save(classmate);

        return R.ok();
    }

    @GetMapping("/info/{classId}")
    @RequiresPermissions("module:classmate:info")
    public R userInfo(@PathVariable("classId") Integer classId){
        Classmate classmate = classmateService.getById(classId);

        return R.ok().put("classmate", classmate);
    }

    @PostMapping("/update")
    @RequiresPermissions("module:classmate:update")
    public R update(@RequestBody Classmate classmate){
        classmate.setCreateTime(LocalDateTime.now());
        classmateService.updateById(classmate);

        return R.ok();
    }

    @PostMapping("/delete")
    @RequiresPermissions("module:classmate:delete")
    public R delete(@RequestBody Integer classId){

        return classmateService.deleteByClassId(classId) > 0 ? R.ok() : R.error("删除失败");
    }

}

