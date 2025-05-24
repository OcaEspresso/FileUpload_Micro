package com.company.fileupload_micro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
*  @description
*  @author: RicksonYu
*  @create: 2025年-05月-24日--16:12
*/
@Data
@Configuration
@ConfigurationProperties(prefix="file")
public class FileStorageConfig {
    private Sftp sftp;
    private List<String> allowedTypes;
    @Data
    public static class Sftp {
        private String host;
        private int port;
        private String username;
        private String password;
        private String remoteDir;
    }
}
