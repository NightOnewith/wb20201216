package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.dao.sys.SysUserMapper;
import com.ethan.ryds.entity.module.Classmate;
import com.ethan.ryds.dao.module.ClassmateMapper;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.service.module.ClassmateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.service.sys.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班级信息 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-30
 */
@Service
public class ClassmateServiceImpl extends ServiceImpl<ClassmateMapper, Classmate> implements ClassmateService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ClassmateMapper classmateMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 拼接查询条件
        LambdaQueryWrapper<Classmate> queryWrapper = new QueryWrapper<Classmate>().lambda();
        if (!params.get("classname").toString().isEmpty()) {
            queryWrapper.like(Classmate::getClassname, params.get("classname"));
        }
        /*if (!params.get("teacher").toString().isEmpty()) {
            String teacher = (String) params.get("teacher");
            LambdaQueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda();
            wrapper.eq(SysUser::getUsername, teacher);
            SysUser user = sysUserMapper.selectOne(wrapper);
            if (user != null) {
                queryWrapper.eq(Classmate::getTeacherId, user.getUserId());
            } else {
                queryWrapper.eq(Classmate::getTeacherId, 0);
            }
        }*/
        queryWrapper.orderByAsc(Classmate::getClassId);

        // 设置分页参数
        IPage<Classmate> page = this.page(new Query<Classmate>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);
        /*List<Classmate> classmates = (List<Classmate>) pageUtils.getList();
        List<Classmate> classmateList = new ArrayList<>();
        for (Classmate classmate : classmates) {
            SysUser user = sysUserMapper.selectById(classmate.getTeacherId());
            classmate.setTeacher(user.getUsername());
            classmateList.add(classmate);
        }
        pageUtils.setList(classmateList);*/

        return pageUtils;
    }

    /**
     * 删除班级
     *
     * 需判断班级下是否有老师或学生(暂不考虑)
     */
    @Override
    public int deleteByClassId(Integer classId) {
        int count = classmateMapper.deleteById(classId);

        return count;
    }

}
