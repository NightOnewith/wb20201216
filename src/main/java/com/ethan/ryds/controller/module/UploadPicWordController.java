package com.ethan.ryds.controller.module;


import com.ethan.ryds.common.utils.FileSizeUtils;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.UploadPicWord;
import com.ethan.ryds.service.module.UploadPicWordService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图片，word文件上传管理 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/uploadPicWord")
public class UploadPicWordController {

    @Autowired
    private UploadPicWordService uploadPicWordService;

    // 文件列表
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = uploadPicWordService.queryPage(params);

        return R.ok().put("page", page);
    }

    // 上传图片 (此处使用单文件 MultipartFile 接收，前端上传多个图片 会多次调用该方法，一张图片上传调用一次！！！)
    @RequestMapping("/pic")
    public R uploadPic(@RequestParam(name = "filename", required = false) MultipartFile file) {
        R r = uploadPicWordService.uploadPic(file);

        return r;
    }

    // 上传word (此处使用单文件 MultipartFile 接收，前端上传多个word 会多次调用该方法，一个word上传调用一次！！！)
    @RequestMapping("/word")
    public R uploadWord(@RequestParam(name = "filename", required = false) MultipartFile file) {
        R r = uploadPicWordService.uploadWord(file);

        return r;
    }

    // 上传图片 (此处为 tinymce编辑器 中的图片上传接口)
    @RequestMapping("/uploadImg")
    public R uploadImg(HttpServletRequest request) {
        R r = uploadPicWordService.uploadImg(request);

        return r;
    }

    @RequestMapping("/update")
    public R updateWordScore(@RequestBody UploadPicWord uploadPicWord) {
        boolean update = uploadPicWordService.updateById(uploadPicWord);

        return update ? R.ok() : R.error("评分失败");
    }

}

