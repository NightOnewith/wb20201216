package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图片，word文件上传管理
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UploadPicWord extends Model<UploadPicWord> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 存储位置
     */
    private String storageLocation;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件链接
     */
    private String fileUrl;

    /**
     * 文件标识
     */
    private Integer flag;

    /**
     * 实验报告评分
     */
    private Integer score;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

    /**
     * 是否编辑分数
     */
    @TableField(exist = false)
    private boolean scoreEdit;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
