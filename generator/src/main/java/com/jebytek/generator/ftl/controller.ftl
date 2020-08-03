package com.jebytek.${module}.controller.admin;

import com.jebytek.server.dto.${Domain}Dto;
import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.service.${Domain}Service;
import com.jebytek.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {
    @Resource
    private ${Domain}Service ${domain}Service;

    /*
    * retrieve all ${domain}s
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto) {

        // saving validation
        <#list fieldList as field>
        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="index">
            <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameComment}");
            </#if>
            <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameComment}", 1, ${field.length?c});
            </#if>
        </#if>
        </#list>

        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.delete(id);
        return responseDto;
    }
}
