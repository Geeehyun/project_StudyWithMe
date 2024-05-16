package org.fullstack4.projectstudywithme.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.fullstack4.projectstudywithme.dto.PageResponseDTO;
import org.fullstack4.projectstudywithme.dto.StudyDTO;
import org.fullstack4.projectstudywithme.service.StudyServiceIf;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Log4j2
@RequestMapping("/main")
@RequiredArgsConstructor
@Controller
public class MainController {
    private final StudyServiceIf studyServiceIf;
    @GetMapping("/main")
    public void GetMain(PageRequestDTO pageRequestDTO,
                        Model model,
                        HttpSession session) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            if(pageRequestDTO.getDate1() == null) {
                String today = String.valueOf(LocalDate.now());
                pageRequestDTO.setDate1(today);
                pageRequestDTO.setDate2(today);
            }
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> pageRequestDTO : {}", pageRequestDTO);
            PageResponseDTO<StudyDTO> myPageDTO = studyServiceIf.list(pageRequestDTO, sessionId);
            model.addAttribute("pageResponseDTO", myPageDTO);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> myPageDTO : {}", myPageDTO);

            pageRequestDTO.setDate1(null);
            pageRequestDTO.setDate2(null);
            pageRequestDTO.setPage_size(2);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> pageRequestDTO : {}", pageRequestDTO);
            PageResponseDTO<StudyDTO> sharedPageDTO = studyServiceIf.listToMe(pageRequestDTO, sessionId);
            model.addAttribute("sharedPageDTO", sharedPageDTO);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>> sharedPageDTO : {}", sharedPageDTO);
        }
    }
}
