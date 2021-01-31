package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.CommentReply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讨论主题下的评论回复 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-13
 */
public interface CommentReplyService extends IService<CommentReply> {

    /**
     * 查询主题下的评论列表
     */
    List<CommentReply> getListByTopicId(Integer ownerId);

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存评论
     */
    R saveCommentReply(CommentReply commentReply);

    /**
     * 删除评论(包含子评论)
     */
    boolean deleteById(Integer id);

}
