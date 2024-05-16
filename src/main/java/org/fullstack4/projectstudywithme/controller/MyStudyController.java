package org.fullstack4.projectstudywithme.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
            log.info("======================================== likeDTO : {}", likeDTO);
            List<String> likeMemberList = new ArrayList<>();
            likeDTO.forEach(dto -> {likeMemberList.add(dto.getMemberId());});
            log.info("======================================== likeMemberList : {}", likeMemberList);
            if(studyDTO.getMessage() != null) {
                redirectAttributes.addFlashAttribute("err",studyDTO.getMessage());
                return "redirect:/mystudy/list";
            } else {
                model.addAttribute("studyDTO", studyDTO);
                model.addAttribute("sharedDTOList", sharedDTO);
                model.addAttribute("likedDTOList", likeDTO);
                model.addAttribute("likeMemberList", likeMemberList);
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
    public String postRegist(StudyDTO studyDTO,
                           @RequestParam(name = "sharedMemberId", defaultValue = "")String sharedMemberId,
                           @RequestParam(name = "sharedMemberName", defaultValue = "")String sharedMemberName,
                           @RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes,
                           HttpSession session,
                           HttpServletRequest request) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            String sessionName = sessionMemberDTO.getMemberName();
            studyDTO.setMemberId(sessionId);
            studyDTO.setMemberName(sessionName);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>studyDTO : {}", studyDTO);
            FileDTO fileDTO = null;
            if (file.getSize() > 0) {
                String uploadFolder = CommonUtil.getUploadFolder(request);
                fileDTO = FileDTO.builder()
                        .file(file)
                        .uploadFolder(uploadFolder)
                        .build();
            } else {
                studyDTO.setThumbnailPath("/upload/");
                studyDTO.setThumbnail("default.jpg");
            }
            List<StudySubDTO> sharedList = new ArrayList<>();
            if (!sharedMemberId.isEmpty() && !sharedMemberName.isEmpty()) {
                String[] sharedMemberIdes = sharedMemberId.split(",");
                String[] sharedMemberNames = sharedMemberName.split(",");
                for (int i = 0; i < sharedMemberIdes.length; i++) {
                    StudySubDTO studySubDTO = new StudySubDTO();
                    studySubDTO.setMemberId(sharedMemberIdes[i]);
                    studySubDTO.setMemberName(sharedMemberNames[i]);
                    sharedList.add(studySubDTO);
                }
            }
            int result = studyServiceIf.registStudy(studyDTO, sharedList, fileDTO);
            if(result > 0 ) {
                redirectAttributes.addFlashAttribute("result", "게시글 정상 등록");
                return "redirect:/mystudy/list";
            } else {
                redirectAttributes.addFlashAttribute("err", "게시글 등록 실패");
                return "redirect:/mystudy/regist";
            }
        }
        return "redirect:/main/main";
    }
    @GetMapping("/modify")
    public String getModify(HttpSession session,
                            String idx,
                            RedirectAttributes redirectAttributes,
                            Model model
                          ) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String memberId = memberDTO.getMemberId();
            int idxToInt = CommonUtil.parseInt(idx);
            Map<String, Object> map = studyServiceIf.view(idxToInt, memberId);
            StudyDTO studyDTO = (StudyDTO) map.get("studyDTO");
            List<StudySubDTO> sharedDTO = (List<StudySubDTO>) map.get("sharedDTOList");
            if(studyDTO.getMessage() != null) {
                redirectAttributes.addFlashAttribute("err",studyDTO.getMessage());
                return "redirect:/mystudy/list";
            } else {
                model.addAttribute("studyDTO", studyDTO);
                model.addAttribute("sharedDTOList", sharedDTO);
                return "/myStudy/modify";
            }
        }
        redirectAttributes.addFlashAttribute("err","잘못된 접근");
        return "redirect:/main/main";
    }

    @PostMapping("/modify")
    public String postModify(StudyDTO studyDTO,
                           @RequestParam(name = "sharedMemberId", defaultValue = "")String sharedMemberId,
                           @RequestParam(name = "sharedMemberName", defaultValue = "")String sharedMemberName,
                           @RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes,
                           HttpSession session,
                           HttpServletRequest request) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            String sessionName = sessionMemberDTO.getMemberName();
            studyDTO.setMemberId(sessionId);
            studyDTO.setMemberName(sessionName);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>studyDTO : {}", studyDTO);
            FileDTO fileDTO = null;
            if (file.getSize() > 0) {
                String uploadFolder = CommonUtil.getUploadFolder(request);
                fileDTO = FileDTO.builder()
                        .file(file)
                        .uploadFolder(uploadFolder)
                        .build();
            }
            List<StudySubDTO> sharedList = new ArrayList<>();
            if (!sharedMemberId.isEmpty() && !sharedMemberName.isEmpty()) {
                String[] sharedMemberIdes = sharedMemberId.split(",");
                String[] sharedMemberNames = sharedMemberName.split(",");
                for (int i = 0; i < sharedMemberIdes.length; i++) {
                    StudySubDTO studySubDTO = new StudySubDTO();
                    studySubDTO.setMemberId(sharedMemberIdes[i]);
                    studySubDTO.setMemberName(sharedMemberNames[i]);
                    sharedList.add(studySubDTO);
                }
            }
            int result = studyServiceIf.modifyStudy(studyDTO, sharedList, fileDTO);
            if(result > 0 ) {
                redirectAttributes.addFlashAttribute("result", "게시글 정상 수정");
                return "redirect:/mystudy/list";
            } else {
                redirectAttributes.addFlashAttribute("err", "게시글 수정 실패");
                redirectAttributes.addAttribute("studyDTO", studyDTO);
                redirectAttributes.addAttribute("sharedDTOList", sharedList);
                return "redirect:/mystudy/modify";
            }
        }
        redirectAttributes.addFlashAttribute("err", "잘못된 접근 입니다.");
        return "redirect:/main/main";
    }

    @PostMapping("/delete")
    public String postDelete(@RequestParam(name = "idx", defaultValue = "0")String idx,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            int result = studyServiceIf.deleteStudy(idx, sessionId);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("result", "게시글 삭제 성공");
                return "redirect:/mystudy/list";
            } else {
                redirectAttributes.addFlashAttribute("err", "게시글 삭제 실패");
                redirectAttributes.addAttribute("idx", idx);
                return "redirect:/mystudy/view";
            }
        }
        redirectAttributes.addFlashAttribute("err", "잘못된 접근 입니다.");
        return "redirect:/main/main";
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String postLikeStudy(@RequestParam(name = "idx", defaultValue = "0") String idx, HttpSession session){
        Map<String, String> resultMap = new HashMap<>();
        if (session.getAttribute("memberDTO") != null) {
            MemberDTO sessionMemberDTO = (MemberDTO) session.getAttribute("memberDTO");
            String sessionId = sessionMemberDTO.getMemberId();
            String sessionName= sessionMemberDTO.getMemberName();
            if(!idx.equals("0")) {
                Map<String, Object> map = studyServiceIf.view(Integer.parseInt(idx), sessionId);
                StudyDTO studyDTO = (StudyDTO) map.get("studyDTO");
                List<StudySubDTO> likeDTO = (List<StudySubDTO>) map.get("likedDTOList");
                List<String> likeMemberList = new ArrayList<>();
                likeDTO.forEach(dto -> {likeMemberList.add(dto.getMemberId());});
                if(!likeMemberList.contains(sessionId)) {
                    int result = studyServiceIf.likeStudy(idx, sessionId, sessionName);
                    if(result > 0) {
                        resultMap.put("result", "success");
                        resultMap.put("message", "좋아요 성공");
                    } else {
                        resultMap.put("result", "fail");
                        resultMap.put("message", "좋아요 실패");
                    }
                } else {
                    resultMap.put("result", "fail");
                    resultMap.put("message", "이미 좋아요한 게시글");
                }
            } else {
                resultMap.put("result", "fail");
                resultMap.put("message", "없는 게시글");
            }
        } else {
            resultMap.put("result", "fail");
            resultMap.put("message", "로그인 정보 없음");
        }
        JSONObject jsonObject = new JSONObject(resultMap);
        return jsonObject.toString();
    }
}
