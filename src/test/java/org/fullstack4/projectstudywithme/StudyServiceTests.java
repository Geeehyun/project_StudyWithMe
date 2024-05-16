package org.fullstack4.projectstudywithme;

import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.fullstack4.projectstudywithme.dto.PageResponseDTO;
import org.fullstack4.projectstudywithme.dto.StudyDTO;
import org.fullstack4.projectstudywithme.service.StudyServiceIf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class StudyServiceTests {
    @Autowired
    private StudyServiceIf studyServiceIf;
//    @Test
//    public void  testList() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .build();
//        PageResponseDTO<StudyDTO> pageResponseDTO = studyServiceIf.list(pageRequestDTO, "test");
//        log.info("pageResponseDTO : {}", pageResponseDTO);
//    }

    @Test
    public void testListFromMe() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .build();
        PageResponseDTO<StudyDTO> pageResponseDTO = studyServiceIf.listFromMe(pageRequestDTO, "test");
        log.info("pageResponseDTO : {}", pageResponseDTO);
    }

    @Test
    public void testListToMe() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .build();
        PageResponseDTO<StudyDTO> pageResponseDTO = studyServiceIf.listToMe(pageRequestDTO, "test");
        log.info("pageResponseDTO : {}", pageResponseDTO);
    }
}
