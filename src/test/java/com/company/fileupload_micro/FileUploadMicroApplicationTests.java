package com.company.fileupload_micro;

import com.company.fileupload_micro.entity.FileMetadataEntity;
import com.company.fileupload_micro.repository.FileMetadataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Commit
@SpringBootTest
@AutoConfigureMockMvc
class FileUploadMicroApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test666.pdf", "application/pdf", "test content".getBytes());

        mockMvc.perform(multipart("/api/files/upload")
                        .file(file))
                .andExpect(status().isOk());
        List<FileMetadataEntity> metadataList = fileMetadataRepository.findAll();
        assertFalse(metadataList.isEmpty(), "File metadata should be saved in database");
        System.out.println("Saved metadata: " + metadataList);
        Thread.sleep(10000);
    }
}