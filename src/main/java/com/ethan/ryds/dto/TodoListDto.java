package com.ethan.ryds.dto;

import lombok.Data;

/**
 * @Description TODO
 * @Author Ethan
 * @Date 2020/6/17 14:09
 */
@Data
public class TodoListDto {

    private Integer key;

    private String label;

    private Integer status;
}
