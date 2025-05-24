初版架构:

file-upload-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/fileupload/
│   │   │       ├── config/
│   │   │       │   └── FileStorageConfig.java        # SFTP 配置类
│   │   │       ├── controller/
│   │   │       │   └── FileUploadController.java     # 文件上传控制器
│   │   │       ├── dto/
│   │   │       │   └── FileMetadata.java             # 文件元数据 DTO
│   │   │       ├── entity/
│   │   │       │   └── FileMetadataEntity.java       # H2 数据库实体
│   │   │       ├── exception/
│   │   │       │   ├── FileStorageException.java     # 自定义异常
│   │   │       │   └── GlobalExceptionHandler.java   # 全局异常处理
│   │   │       ├── repository/
│   │   │       │   └── FileMetadataRepository.java   # JPA 仓库
│   │   │       ├── service/
│   │   │       │   ├── FileStorageService.java       # 文件存储服务接口
│   │   │       │   └── impl/
│   │   │       │       └── SftpFileStorageServiceImpl.java # SFTP 实现
│   │   │       └── FileUploadApplication.java        # 主应用类
│   │   ├── resources/
│   │       ├── application.yml                       # 配置文件
│   │       └── logback-spring.xml                   # 日志配置
│   ├── test/
│   │   └── java/
│   │       └── com/example/fileupload/
│   │           └── controller/
│   │               └── FileUploadControllerTest.java # 单元测试
├── pom.xml                                           # Maven 依赖
└── README.md                                         # 项目说明