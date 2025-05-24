package com.company.fileupload_micro.controller;

import com.company.fileupload_micro.dto.FileMetadata;
import com.company.fileupload_micro.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileUploadController.class)
class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    @Test
    void testUploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "testt1.pdf", "application/pdf", "content".getBytes());
        FileMetadata metadata = new FileMetadata();
        metadata.setId(1L);
        metadata.setFileName("uuid.pdf");
        metadata.setOriginalFileName("test.pdf");
        metadata.setFileType("application/pdf");
        metadata.setStoragePath("/opt/filesstorage/upload/uuid.pdf");

        when(fileStorageService.storeFile(any())).thenReturn(metadata);

        mockMvc.perform(multipart("/api/files/upload")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("uuid.pdf"))
                .andExpect(jsonPath("$.originalFileName").value("test.pdf"))
                .andExpect(jsonPath("$.fileType").value("application/pdf"))
                .andExpect(jsonPath("$.storagePath").value("/opt/filesstorage/upload/uuid.pdf"));
    }
}