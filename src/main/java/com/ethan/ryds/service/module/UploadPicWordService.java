package com.ethan.ryds.service.module;

import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.UploadPicWord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 图片，word文件上传管理 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
public interface UploadPicWordService extends IService<UploadPicWord> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 上传图片
     */
    R uploadPic(MultipartFile file);

    /**
     * 上传word
     */
    R uploadWord(MultipartFile file);

    /**
     * 上传img
     */
    R uploadImg(HttpServletRequest request);

}
