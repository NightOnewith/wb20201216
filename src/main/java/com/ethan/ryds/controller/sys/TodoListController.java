package com.ethan.ryds.controller.sys;

import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dto.TodoListDto;
import com.ethan.ryds.dto.TodoListForm;
import com.ethan.ryds.entity.sys.TodoList;
import com.ethan.ryds.service.sys.TodoListService;
import com.ethan.ryds.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author Ethan
 * @Date 2020/6/17 11:47
 */
@RestController
@RequestMapping("/todo")
public class TodoListController extends AbstractController {

    @Autowired
    private TodoListService todoListService;

    @GetMapping("/list")
    public R list() {
        TodoListDto[] todoLists = todoListService.getList();
        Integer[] statusOf2 = todoListService.selectByStatusOf2();

        return R.ok().put("list", todoLists).put("isOver", statusOf2);
    }

    @RequestMapping("/update")
    public R update(@RequestBody TodoListForm form) {
        for (int i = 0; i < form.getIds().length; i++) {
            TodoList todoList = new TodoList();
            todoList.setId(form.getIds()[i]);
            todoList.setStatus(form.getIsOver());

            todoListService.updateById(todoList);
        }

        return R.ok();
    }

}
