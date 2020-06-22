package com.ethan.ryds.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author Ethan
 * @Date 2020/6/17 15:18
 */
@Data
public class TodoListForm {

    private Integer[] ids;

    private Integer isOver;
}
