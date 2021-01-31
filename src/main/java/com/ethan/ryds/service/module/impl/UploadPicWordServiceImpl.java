package com.ethan.ryds.service.module.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ethan.ryds.common.utils.FileSizeUtils;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import com.ethan.ryds.common.utils.R;
import com.ethan.ryds.entity.module.UploadPicWord;
import com.ethan.ryds.dao.module.UploadPicWordMapper;
import com.ethan.ryds.service.module.UploadPicWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图片，word文件上传管理 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
@Service
public class UploadPicWordServiceImpl extends ServiceImpl<UploadPicWordMapper, UploadPicWord> implements UploadPicWordService {

    // 文件的真实路径
    @Value("${file.uploadFolder}")
    private String realBasePath;

    // 文件映射路径
    @Value("${file.accessPath}")
    private String accessPath;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String fileName = (String) params.get("fileName");
        String fileType = (String) params.get("fileType");
        Integer flag = 0;

        if ("image".equals(fileType)) {
            flag = 1;
        } else if ("word".equals(fileType)) {
            flag = 2;
        }

        IPage<UploadPicWord> page = this.page(
                new Query<UploadPicWord>().getPage(params),
                new QueryWrapper<UploadPicWord>().lambda()
                        .eq(UploadPicWord::getFlag, flag)
                        .like(StringUtils.isNotBlank(fileName), UploadPicWord::getFileName, fileName)
                        .orderByDesc(UploadPicWord::getId)
        );

        PageUtils pageUtils = new PageUtils(page);
        List<UploadPicWord> list = (List<UploadPicWord>) pageUtils.getList();
        for (int i = 0; i < list.size(); i++) {
            UploadPicWord uploadPicWord = list.get(i);
            String showFilename = uploadPicWord.getFileName().substring(uploadPicWord.getFileName().lastIndexOf("-") + 1, uploadPicWord.getFileName().length());
            uploadPicWord.setFileName(showFilename);
            uploadPicWord.setScoreEdit(false);
        }

        pageUtils.setList(list);

        return pageUtils;
    }

    @Override
    public R uploadPic(MultipartFile file) {
        // 获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());

        // 获取文件类型
        String contentType = file.getContentType();

        // 获取文件大小
        long size = file.getSize();
        String fileSize = FileSizeUtils.transformFileSize(size);

        // 判断上传图片格式
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return R.error("图片格式不正确");
        }

        //  windows 生成到项目中/upload目录下
        // String uploadPath = uploadPath(1);

        // 上传Linux服务器存储路径
        String uploadPath = realBasePath;

        File savePath = new File(uploadPath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        // 通过 UUID 生成唯一文件名
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            // 图片url
            String picUrl = uploadPath + filename;

            // 将文件保存指定目录
            file.transferTo(new File(picUrl));

            /*================ 图片上传服务器成功后，保存到数据库 ================*/

            // 文件映射路径，浏览器中的访问路径
            String url = accessPath + filename;

            UploadPicWord picWord = new UploadPicWord();
            picWord.setFileName(filename);
            picWord.setFileType(contentType);
            picWord.setStorageLocation("本地");
            picWord.setFileSize(fileSize);
            picWord.setFileUrl(url);
            picWord.setFlag(1);
            picWord.setCreateTime(LocalDateTime.now());

            boolean save = this.saveOrUpdate(picWord);

            if (!save) {
                return R.error("上传失败！");
            }

            return R.ok("上传成功！").put("picUrl", url);
        } catch (Exception e) {
            e.printStackTrace();

            return R.error("上传失败！");
        }
    }

    @Override
    public R uploadWord(MultipartFile file) {
        // 获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());

        // 获取文件类型
        String contentType = file.getContentType();

        // 获取文件大小
        long size = file.getSize();
        String fileSize = FileSizeUtils.transformFileSize(size);

        // 判断上传 word 格式
        if (!"doc,docx".toUpperCase().contains(suffix.toUpperCase())) {
            return R.error("word格式不正确");
        }

        //  Windows 生成到项目中/upload目录下
        // String uploadPath = uploadPath(2);

        // 上传Linux服务器存储路径
        String uploadPath = realBasePath;

        File savePath = new File(uploadPath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        // 通过 UUID 生成唯一文件名
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            // 图片url
            String docUrl = uploadPath + filename;

            // 将文件保存指定目录
            file.transferTo(new File(docUrl));

            /*================ 图片上传服务器成功后，保存到数据库 ================*/

            // 文件映射路径，浏览器中的访问路径
            String url = accessPath + filename;

            UploadPicWord picWord = new UploadPicWord();
            picWord.setFileName(filename);
            picWord.setFileType(contentType);
            picWord.setStorageLocation("本地");
            picWord.setFileSize(fileSize);
            picWord.setFileUrl(url);
            picWord.setFlag(2);
            picWord.setCreateTime(LocalDateTime.now());

            boolean save = this.saveOrUpdate(picWord);

            if (!save) {
                return R.error("上传失败！");
            }

            return R.ok("上传成功！").put("docUrl", url);
        } catch (Exception e) {
            e.printStackTrace();

            return R.error("上传失败！");
        }
    }

    @Override
    public R uploadImg(HttpServletRequest request) {
        // 取出form-data中的二进制字段
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // file是form-data中二进制字段对应的name
        MultipartFile file = multipartRequest.getFile("file");

        // 获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());

        // 获取文件类型
        String contentType = file.getContentType();

        // 获取文件大小
        long size = file.getSize();
        String fileSize = FileSizeUtils.transformFileSize(size);

        // 判断上传 word 格式
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            return R.error("图片格式不正确");
        }

        //  生成到项目中/upload目录下
        // String uploadPath = uploadPath(1);

        // 上传Linux服务器存储路径
        String uploadPath = realBasePath;

        File savePath = new File(uploadPath);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        // 通过 UUID 生成唯一文件名
        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            // 图片url
            String imgUrl = uploadPath + filename;

            // 将文件保存指定目录
            if (file.getSize() <= 0) {
                return R.error("上传失败");
            } else { // 上传
                InputStream is = null;
                OutputStream out = null;
                try {
                    is = file.getInputStream();
                    out = new FileOutputStream(imgUrl);
                    IOUtils.copy(is, out);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            /*================ 图片上传服务器成功后，保存到数据库 ================*/

            // 文件映射路径，浏览器中的访问路径
            String url = accessPath + filename;

            UploadPicWord picWord = new UploadPicWord();
            picWord.setFileName(filename);
            picWord.setFileType(contentType);
            picWord.setStorageLocation("本地");
            picWord.setFileSize(fileSize);
            picWord.setFileUrl(url);
            picWord.setFlag(1);
            picWord.setCreateTime(LocalDateTime.now());

            boolean save = this.saveOrUpdate(picWord);

            if (!save) {
                return R.error("上传失败！");
            }

            return R.ok("上传成功！").put("imgUrl", url);
        } catch (Exception e) {
            e.printStackTrace();

            return R.error("上传失败！");
        }
    }

    /**
     * 获取服务器项目文件目录
     * @return
     * @throws IOException
     */
    public static String uploadPath(int fileType) {
        //  获取项目根目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!path.exists()) path = new File("");

        //  文件上传目录为/static/upload/，如下获取：
        File upload = null;

        // windows
        if (fileType == 1) { // 图片
            upload = new File(path.getAbsolutePath(),"static/upload/img");
        } else if (fileType == 2) { // word
            upload = new File(path.getAbsolutePath(),"static/upload/word");
        }

        if(!upload.exists()) upload.mkdirs();

        return upload.getAbsolutePath();
    }

}
