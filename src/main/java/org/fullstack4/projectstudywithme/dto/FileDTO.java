package org.fullstack4.projectstudywithme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private String uploadFolder;
    private MultipartFile file;
}
