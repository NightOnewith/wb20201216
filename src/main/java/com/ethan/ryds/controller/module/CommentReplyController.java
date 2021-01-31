package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.CommentReply;
import com.ethan.ryds.service.module.CommentReplyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讨论主题下的评论回复 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-13
 */
@RestController
@RequestMapping("/commentReply")
public class CommentReplyController {

    @Autowired
    private CommentReplyService commentReplyService;

    @RequestMapping("/list")
    @RequiresPermissions("comment:reply:list")
    public R list(@RequestParam Map<String ,Object> params) {
        PageUtils page = commentReplyService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/info/{id}")
    public R list(@PathVariable("id") Integer ownerId) {
        List<CommentReply> list = commentReplyService.getListByTopicId(ownerId);

        return R.ok().put("data", list);
    }

    @RequestMapping("/save")
    public R save(@RequestBody CommentReply commentReply) {
        R r = commentReplyService.saveCommentReply(commentReply);

        return r;
    }

    @RequestMapping("/delete")
    @RequiresPermissions("comment:reply:delete")
    public R delete(@RequestBody Integer id) {
        boolean remove = commentReplyService.deleteById(id);

        return remove ? R.ok() : R.error("删除失败");
    }

}

