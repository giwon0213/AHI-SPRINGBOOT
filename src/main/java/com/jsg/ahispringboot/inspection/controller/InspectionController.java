package com.jsg.ahispringboot.inspection.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsg.ahispringboot.common.ResponseDTO;
import com.jsg.ahispringboot.inspection.dto.AnswerDTO;
import com.jsg.ahispringboot.inspection.dto.ModifyResumeDTO;
import com.jsg.ahispringboot.inspection.dto.ReaderDTO;
import com.jsg.ahispringboot.inspection.dto.ResumeDTO;
import com.jsg.ahispringboot.inspection.dto.SelfIntroductionDTO;
import com.jsg.ahispringboot.inspection.service.InspectionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionsService;

    public InspectionController(InspectionService inspectionService) {
        this.inspectionsService = inspectionService;
    }

    @GetMapping("/getResumeList")
    public ResponseEntity<ResponseDTO> selectResume() {
        Long code = 3L;
        List<ResumeDTO> resume = inspectionsService.selectMemberResume(code);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "성공", resume));
    }

    @GetMapping("/getResume/{resumeCode}")
    public ResponseEntity<ResponseDTO> selectResume(@PathVariable("resumeCode") Long resumeCode) {
        log.info("resumeCode : {} ", resumeCode);
        Long userCode = 3L;
        ReaderDTO reader = inspectionsService.selcetResumeDetall(resumeCode, userCode);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "성공 bro", reader));
    }

    @PostMapping("/modifyResume")
    public ResponseEntity<ResponseDTO> modifyResume(@RequestBody ModifyResumeDTO modifyResumeDTO) {
        System.out.println(modifyResumeDTO);
        AnswerDTO modifyResume = inspectionsService.modifyResume(modifyResumeDTO);
        log.info("Controller : {} ", modifyResume);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "통신완료", modifyResume));
    }

}
