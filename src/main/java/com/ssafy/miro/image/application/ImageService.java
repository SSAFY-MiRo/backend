package com.ssafy.miro.image.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {
    public String saveFile(MultipartFile file) throws IOException {
        // 저장 경로 설정 (서버 내의 uploads 디렉토리)
        String uploadDir = System.getProperty("user.dir") + "/uploads/"; // 프로젝트 루트 경로에 저장
        System.out.println(uploadDir);

        // 디렉토리 생성 (없을 경우)
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 이름 생성 (중복 방지를 위해 UUID 사용)
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 전체 파일 경로
        Path filePath = uploadPath.resolve(fileName);

        // 파일 저장
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }
}
