package com.ethan.ryds.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.entity.sys.SysLog;
import com.ethan.ryds.common.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-21
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);
}
