package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 网站访问量
 * </p>
 *
 * @author Ethan
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PageView extends Model<PageView> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 页面访问量
     */
    private Integer views;

    /**
     * 访问页面URL
     */
    private String pageUrl;

    /**
     * 统计日期
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
