package com.ethan.ryds.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.dto.TodoListDto;
import com.ethan.ryds.entity.sys.TodoList;

/**
 * @Description TodoList Service接口
 * @Author Ethan
 * @Date 2020/6/17 11:44
 */
public interface TodoListService extends IService<TodoList> {

    TodoListDto[] getList();

    Integer[] selectByStatusOf2();

}
