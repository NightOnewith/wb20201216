package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.CommentReply;
import com.ethan.ryds.dao.module.CommentReplyMapper;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.service.module.CommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.service.sys.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讨论主题下的评论回复 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-13
 */
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {

    @Autowired
    private CommentReplyMapper commentReplyMapper;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<CommentReply> getListByTopicId(Integer ownerId) {
        /* 父评论列表 */
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getOwnerId, ownerId);
        queryWrapper.eq(CommentReply::getCommentId, 0);
        queryWrapper.orderByDesc(CommentReply::getId);
        List<CommentReply> fatherReplyList = commentReplyMapper.selectList(queryWrapper);

        /* 子评论列表 */
        for (CommentReply commentReply : fatherReplyList) {
            LambdaQueryWrapper<CommentReply> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CommentReply::getOwnerId, commentReply.getOwnerId());
            wrapper.eq(CommentReply::getCommentId, commentReply.getId());
            List<CommentReply> childrenReplyList = commentReplyMapper.selectList(wrapper);

            if (childrenReplyList.size() == 0) {
                commentReply.setReply(null);
            } else {
                commentReply.setReply(childrenReplyList);
            }
        }

        return fatherReplyList;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String fromName = (String) params.get("fromName");
        String content = (String) params.get("content");

        IPage<CommentReply> page = this.page(
                new Query<CommentReply>().getPage(params),
                new QueryWrapper<CommentReply>().lambda()
                        .like(StringUtils.isNotBlank(fromName), CommentReply::getFromName, fromName)
                        .like(StringUtils.isNotBlank(content), CommentReply::getContent, content)
                        .orderByDesc(CommentReply::getId)
        );

        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

    @Override
    public R saveCommentReply(CommentReply commentReply) {
        String contentInput = commentReply.getContent().trim();
        if (contentInput == null || contentInput.equals("")) {
            return R.error("请输入评论内容！");
        }

        // 子评论
        if (contentInput.substring(0, 1).equals("@")) {
            String content_Input = contentInput.split("@")[1];
            String[] content_input = content_Input.split(":");
            if (content_input.length < 2) {
                return R.error("请输入评论内容！");
            }

            String username = content_input[0];
            String content = content_input[1].trim();

            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getUsername, username);
            SysUser user = sysUserService.getOne(queryWrapper);

            commentReply.setContent(content);
            commentReply.setToId(user.getUserId());
            commentReply.setToName(username);
        }

        commentReply.setDate(LocalDateTime.now());
        int save = commentReplyMapper.insert(commentReply);

        return save > 0 ? R.ok("评论成功") : R.error("评论失败");
    }

    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        commentReplyMapper.deleteById(id);

        // 将评论下的子评论一起删除
        LambdaQueryWrapper<CommentReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentReply::getCommentId, id);
        commentReplyMapper.delete(queryWrapper);

        return true;
    }
}
