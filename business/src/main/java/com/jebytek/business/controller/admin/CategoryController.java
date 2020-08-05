package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.CategoryDto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.CategoryService;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /*
    * retrieve all categorys
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        categoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto) {

        // saving validation
        ValidatorUtil.require(categoryDto.getParent(), "Parent ID");
        ValidatorUtil.require(categoryDto.getName(), "Name");
        ValidatorUtil.length(categoryDto.getName(), "Name", 1, 50);

        ResponseDto responseDto = new ResponseDto();
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        categoryService.delete(id);
        return responseDto;
    }
}
