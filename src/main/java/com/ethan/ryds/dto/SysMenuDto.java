package com.ethan.ryds.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 用户动态路由表
 * Created by ASUS on 2020/6/5.
 */
@Data
public class SysMenuDto {

    private Long id;

    private String name;

    private Integer type;

    private String path;

    private String component;

    private Meta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> children;

    @Data
    public static class Meta {

        private String icon;

        private String title;
    }
}
