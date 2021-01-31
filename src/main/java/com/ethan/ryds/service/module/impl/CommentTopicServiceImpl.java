package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.entity.module.CommentReply;
import com.ethan.ryds.entity.module.CommentTopic;
import com.ethan.ryds.dao.module.CommentTopicMapper;
import com.ethan.ryds.entity.module.TeachingTeam;
import com.ethan.ryds.service.module.CommentReplyService;
import com.ethan.ryds.service.module.CommentTopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讨论区主题表 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@Service
public class CommentTopicServiceImpl extends ServiceImpl<CommentTopicMapper, CommentTopic> implements CommentTopicService {

    @Autowired
    private CommentReplyService commentReplyService;

    @Autowired
    private CommentTopicMapper commentTopicMapper;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String account = (String) params.get("account");

        IPage<CommentTopic> page = this.page(
                new Query<CommentTopic>().getPage(params),
                new QueryWrapper<CommentTopic>().lambda()
                        .like(StringUtils.isNotBlank(title), CommentTopic::getTitle, title)
                        .like(StringUtils.isNotBlank(account), CommentTopic::getAccount, account)
                        .orderByDesc(CommentTopic::getId)
        );

        PageUtils pageUtils = new PageUtils(page);
        List<CommentTopic> list = (List<CommentTopic>) pageUtils.getList();
        for (CommentTopic commentTopic : list) {
            LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CommentReply::getOwnerId, commentTopic.getId());
            int count = commentReplyService.count(queryWrapper);
            commentTopic.setReplyCount(count);
        }

        pageUtils.setList(list);

        return pageUtils;
    }

    @Transactional
    @Override
    public boolean deleteCommentTopic(Integer id) {
        commentTopicMapper.deleteById(id);

        // 将主题帖下的所有评论一起删除
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getOwnerId, id);
        boolean remove = false;
        if (commentReplyService.list(queryWrapper).size() > 0) {
            remove = commentReplyService.remove(queryWrapper);
        } else {
            remove = true;
        }

        return remove;
    }

}
