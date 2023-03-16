package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.dto.model.UploadedFileDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.service.FileService;
import com.onix.hcmustour.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
public class FileController {
    @Autowired
    public FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Response> upload(@RequestParam("file") MultipartFile[] files) {
        try {
            List<UploadedFileDto> uploadedFiles = new ArrayList<>();
            for (MultipartFile file : files) {
                log.info("FileController::upload file {}", file.getOriginalFilename());
                String fileName = fileService.save(file);
                String url = fileService.getImageUrl(fileName);

                uploadedFiles.add(new UploadedFileDto().setFileName(fileName).setUrl(url));
            }
            Response<Object> response = Response.ok().setPayload(uploadedFiles);
            log.info("FileController::upload response {}", ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("FileController::upload error response {}", ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
