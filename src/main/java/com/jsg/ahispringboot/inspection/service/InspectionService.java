package com.jsg.ahispringboot.inspection.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.jsg.ahispringboot.inspection.dto.AnswerDTO;
import com.jsg.ahispringboot.inspection.dto.ModifyResumeDTO;
import com.jsg.ahispringboot.inspection.dto.ReaderDTO;
import com.jsg.ahispringboot.inspection.dto.ResumeDTO;
import com.jsg.ahispringboot.inspection.dto.SelfIntroductionDTO;
import com.jsg.ahispringboot.inspection.entity.Resume;
import com.jsg.ahispringboot.inspection.repository.InspectionRepository;
import com.jsg.ahispringboot.inspection.utils.FileUtils;
import com.jsg.ahispringboot.inspection.utils.FileUtilsImpl;

import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InspectionService {

    private final InspectionRepository inspectionRepositroy;
    private final ModelMapper modelMapper;
    private final FileUtils fileUtils;
    @Value("${fastapi.endpoint}")
    private String endPoint;

    public InspectionService(InspectionRepository inspectionRepositroy,
            ModelMapper modelMapper,
            FileUtilsImpl fileUtilsImpl) {
        this.inspectionRepositroy = inspectionRepositroy;
        this.modelMapper = modelMapper;
        this.fileUtils = fileUtilsImpl;
    }

    public List<ResumeDTO> selectMemberResume(Long memberId) {

        List<Resume> Resumes = inspectionRepositroy.FindById(memberId);
        List<ResumeDTO> resumeDTO = Resumes.stream().map(Resume -> modelMapper.map(Resume, ResumeDTO.class))
                .collect(Collectors.toList());

        for (int i = 0; i < resumeDTO.size(); i++) {
            String path = resumeDTO.get(i).getResumePath();
            String[] spits = path.split("/");
            System.out.println(spits);
            String exe = spits[spits.length - 1];
            String title = exe.replace(".pdf", "");
            resumeDTO.get(i).setResumePath(title);
        }
        return resumeDTO;

    }

    public ReaderDTO selcetResumeDetall(Long resumeCode, Long userCode) {

        Long beforeTime = System.currentTimeMillis();
        Resume resume = inspectionRepositroy.findResumeCode(resumeCode, userCode);
        ResumeDTO resumeDTO = modelMapper.map(resume, ResumeDTO.class);
        ByteArrayResource resource = fileUtils.FileToByteArray(resumeDTO.getResumePath());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = fileUtils.FileCreatebody(resource, "file");
        ReaderDTO reader = fileUtils.GetJsonData(endPoint, requestEntity);
        Long afterTime = System.currentTimeMillis();
        Long diffTime = (afterTime - beforeTime) / 1000;
        log.info("실행 시간(sec) : " + diffTime);
        for (SelfIntroductionDTO s : reader.getSelfIntroductionDTO()) {
            log.info("s : {}", s);
        }
        log.info("reader : {}", reader.getPersonalInformationDTO());
        return reader;

    }

    public AnswerDTO modifyResume(ModifyResumeDTO modifyResumeDTO) {

        Long beforeTime = System.currentTimeMillis();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = fileUtils.ListCreatebody(modifyResumeDTO, "modify");
        AnswerDTO modifyResume = fileUtils.ModifyJsonData(endPoint, httpEntity);
        log.info("modifyEndPoint : {}", modifyResume);
        Long afterTime = System.currentTimeMillis();
        Long diffTime = (afterTime - beforeTime) / 1000;
        log.info("소요시간 : {}", diffTime);

        return modifyResume;
    }

    @Transactional
    public Map<String, Object> imageToPdf(String resumCode, MultipartFile image) {

        Long userCode = 1L;
        LocalDateTime date = LocalDateTime.now();
        String newDate = date.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
        Long code = Long.parseLong(resumCode);
        Resume resume = inspectionRepositroy.findResumeCode(code, userCode);
        ResumeDTO resumeDTO = modelMapper.map(resume, ResumeDTO.class);
        String title = fileUtils.getTitle(resumeDTO.getResumePath());
        ByteArrayResource resource = fileUtils.UploadFileToByteArray(image, title);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = fileUtils.UploadFileCreatebody(resource, "file");
        byte[] barr = fileUtils.getPdf(endPoint, httpEntity);
        String path = fileUtils.SavePdf(barr, resumeDTO.getMember().getName(), title);
        log.info("path : {}", path);

        Resume modifyResume = new Resume();
        modifyResume.setResumePath(path);
        modifyResume.setCreateDate(newDate);
        modifyResume.setMember(resume.getMember());
        ;
        inspectionRepositroy.save(modifyResume);

        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("pdf", barr);
        log.info("map : {}", map);

        return map;
    }

}
