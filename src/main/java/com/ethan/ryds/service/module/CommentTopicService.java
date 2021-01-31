package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.entity.module.CommentTopic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讨论区主题表 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
public interface CommentTopicService extends IService<CommentTopic> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 删除主题帖（将主题帖下的所有评论一起删除）
     */
    boolean deleteCommentTopic(Integer id);

}
