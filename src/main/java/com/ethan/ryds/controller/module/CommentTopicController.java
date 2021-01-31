package com.ethan.ryds.controller.module;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.entity.module.CommentReply;
import com.ethan.ryds.entity.module.CommentTopic;
import com.ethan.ryds.service.module.CommentReplyService;
import com.ethan.ryds.service.module.CommentTopicService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 讨论区主题表 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@RestController
@RequestMapping("/commentTopic")
public class CommentTopicController extends AbstractController {

    @Autowired
    private CommentTopicService commentTopicService;

    @RequestMapping("/list")
    // @RequiresPermissions("comment:topic:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = commentTopicService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/save")
    public R save(@RequestBody CommentTopic commentTopic) {
        commentTopic.setCreateTime(LocalDateTime.now());

        boolean save = commentTopicService.save(commentTopic);

        return save ? R.ok("发布主题帖成功") : R.error("发布主题帖失败");
    }

    @RequestMapping("/delete")
    @RequiresPermissions("comment:topic:delete")
    public R delete(@RequestBody Integer id) {
        boolean remove = commentTopicService.deleteCommentTopic(id);

        return remove ? R.ok() : R.error("删除失败");
    }

    @GetMapping("/info/{id}")
    public R userInfo(@PathVariable("id") Integer id){
        CommentTopic commentTopic = commentTopicService.getById(id);

        return R.ok().put("data", commentTopic);
    }

}

