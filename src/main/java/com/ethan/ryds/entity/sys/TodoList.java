package com.ethan.ryds.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description 任务清单
 * @Author Ethan
 * @Date 2020/6/17 11:40
 */
@Data
@TableName("todo_list")
public class TodoList {

    @TableId(value = "id")
    private Integer id;

    private String label;

    /**
     * 0:执行中  1:已完成
     */
    private Integer status;
}
