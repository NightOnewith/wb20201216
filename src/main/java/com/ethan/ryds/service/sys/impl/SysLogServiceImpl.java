package com.ethan.ryds.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.dao.sys.SysLogMapper;
import com.ethan.ryds.entity.sys.SysLog;
import com.ethan.ryds.service.sys.SysLogService;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-21
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String username = (String) params.get("userName");
        String operation = (String) params.get("operation");

        IPage<SysLog> page = this.page(
                new Query<SysLog>().getPage(params),
                new QueryWrapper<SysLog>().lambda()
                        .like(StringUtils.isNotBlank(username), SysLog::getUsername, username)
                        .like(StringUtils.isNotBlank(operation), SysLog::getOperation, operation)
                        .orderByDesc(SysLog::getId)
        );

        return new PageUtils(page);
    }

}
