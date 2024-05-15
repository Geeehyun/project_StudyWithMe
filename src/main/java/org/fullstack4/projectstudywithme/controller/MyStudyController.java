package org.fullstack4.projectstudywithme.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.Common.CommonUtil;
import org.fullstack4.projectstudywithme.dto.*;
import org.fullstack4.projectstudywithme.service.StudyServiceIf;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("/mystudy")
@RequiredArgsConstructor
@Controller
public class MyStudyController {
    private final StudyServiceIf studyServiceIf;
    @GetMapping("/list")
    public void getList(Model model, HttpSession session, PageRequestDTO pageRequestDTO) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String memberId = memberDTO.getMemberId();
            PageResponseDTO<StudyDTO> pageResponseDTO = studyServiceIf.list(pageRequestDTO, memberId);
            pageResponseDTO.getDtoList().forEach(StudyDTO::setDate);
            log.info("==============================================================================");
            log.info("getList                           pageResponseDTO : {}", pageResponseDTO);
            model.addAttribute(pageResponseDTO);
        }
    }
    @GetMapping("/view")
    public String getView(String idx, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String memberId = memberDTO.getMemberId();
            int idxToInt = CommonUtil.parseInt(idx);
            Map<String, Object> map = studyServiceIf.view(idxToInt, memberId);
            StudyDTO studyDTO = (StudyDTO) map.get("studyDTO");
            List<StudySubDTO> sharedDTO = (List<StudySubDTO>) map.get("sharedDTOList");
            List<StudySubDTO> likeDTO = (List<StudySubDTO>) map.get("likedDTOList");
            if(studyDTO.getMessage() != null) {
                redirectAttributes.addFlashAttribute("err",studyDTO.getMessage());
                return "redirect:/mystudy/list";
            } else {
                model.addAttribute("studyDTO", studyDTO);
                model.addAttribute("sharedDTOList", sharedDTO);
                model.addAttribute("likedDTOList", likeDTO);
                return "/myStudy/view";
            }
        }
        redirectAttributes.addFlashAttribute("err","잘못된 접근");
        return "redirect:/main/main";
    }
    @RequestMapping(value = "/selectMember", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String postSelectMember(String memberId, HttpSession session) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            MemberDTO memberDTO = studyServiceIf.selectMember(memberId, sessionId);
            if (memberDTO.getMessage() == null) {
                Map<String, Object> data = new HashMap<>();
                data.put("message", "조회 성공");
                data.put("memberId", memberDTO.getMemberId());
                data.put("memberName", memberDTO.getMemberName());
                jsonObject = new JSONObject(data);
            } else {
                jsonObject.put("message", memberDTO.getMessage());
            }
        } else {
            jsonObject.put("message", "조회 실패");
        }
        return jsonObject.toString();
    }
    @GetMapping("/regist")
    public void getRegist() {}

    @PostMapping("/regist")
    public void postRegist(StudyDTO studyDTO,
                           @RequestParam(name = "sharedMemberId", defaultValue = "")String sharedMemberId,
                           @RequestParam(name = "sharedMemberName", defaultValue = "")String sharedMemberName,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            String sessionName = sessionMemberDTO.getMemberName();
            studyDTO.setMemberId(sessionId);
            studyDTO.setMemberName(sessionName);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>studyDTO : {}", studyDTO);
            int idx = studyServiceIf.registStudy(studyDTO);
            if(idx > 0) {
                if(!sharedMemberId.isEmpty() && !sharedMemberName.isEmpty()) {
                    String[] sharedMemberIdes = sharedMemberId.split(",");
                    String[] sharedMemberNames = sharedMemberName.split(",");
                    int result = 0;
                    for(int i = 0; i < sharedMemberIdes.length; i++) {
                        StudySubDTO studySubDTO = new StudySubDTO();
                        studySubDTO.setStudyIdx(CommonUtil.parseString(idx));
                        studySubDTO.setMemberId(sharedMemberIdes[i]);
                        studySubDTO.setMemberName(sharedMemberNames[i]);
                        int subIdx = studyServiceIf.registShare(studySubDTO);
                        if(subIdx > 0) {result ++;};
                    }
                    if(sharedMemberIdes.length != result) {
                        redirectAttributes.addFlashAttribute("err", "공유 대상 부분적 등록 실패");
                    }
                } else {
                    redirectAttributes.addFlashAttribute("err", "공유 대상 등록 실패");
                }
            } else {
                redirectAttributes.addFlashAttribute("err", "학습 등록 실패");
            }
        }


    }
    @GetMapping("/modify")
    public void getModify() {}
}
