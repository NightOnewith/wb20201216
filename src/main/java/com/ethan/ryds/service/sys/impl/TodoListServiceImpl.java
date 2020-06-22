package com.ethan.ryds.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.dao.sys.TodoListMapper;
import com.ethan.ryds.dto.TodoListDto;
import com.ethan.ryds.entity.sys.TodoList;
import com.ethan.ryds.service.sys.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TodoList Service实现类
 * @Author Ethan
 * @Date 2020/6/17 11:45
 */
@Service
public class TodoListServiceImpl extends ServiceImpl<TodoListMapper, TodoList> implements TodoListService {

    @Autowired
    private TodoListMapper todoListMapper;

    @Override
    public TodoListDto[] getList() {
        List<TodoList> todoLists = todoListMapper.selectList(null);

        TodoListDto[] dtos = new TodoListDto[todoLists.size()];
        for (int i = 0; i < todoLists.size(); i++) {
            TodoListDto dto = new TodoListDto();
            dto.setKey(todoLists.get(i).getId());
            dto.setLabel(todoLists.get(i).getLabel());
            dto.setStatus(todoLists.get(i).getStatus());

            dtos[i] = dto;
        }

        return dtos;
    }

    @Override
    public Integer[] selectByStatusOf2() {
        LambdaQueryWrapper<TodoList> queryWrapper = new QueryWrapper<TodoList>().lambda();
        queryWrapper.eq(TodoList::getStatus, 1);
        List<TodoList> todoListsOver = todoListMapper.selectList(queryWrapper);

        Integer[] isOver = new Integer[todoListsOver.size()];
        for (int i = 0; i < todoListsOver.size(); i++) {
            isOver[i] = todoListsOver.get(i).getId();
        }

        return isOver;
    }
}
