package com.jebytek.business.controller.admin;

import com.jebytek.server.dto.PageDto;
import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.dto.SectionDto;
import com.jebytek.server.service.SectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {
    @Resource
    private SectionService sectionService;

    /*
    * retrieve all sections
    * */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto) {
        ResponseDto responseDto = new ResponseDto();
        sectionService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    /*
    * save a record
    * when id is empty, insert;
    * when id is not empty, update
    * */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto) {

        // saving validation
        // TODO

        ResponseDto responseDto = new ResponseDto();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    /*
    * delete
    * */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id) {
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}
