package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import org.fullstack4.projectstudywithme.dto.*;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface StudyServiceIf {
    public PageResponseDTO<StudyDTO> list(PageRequestDTO pageRequestDTO, String memberId);
    public PageResponseDTO<StudyDTO> listFromMe(PageRequestDTO pageRequestDTO, String memberId);
    public PageResponseDTO<StudyDTO> listToMe(PageRequestDTO pageRequestDTO, String memberId);
    public Map<String, Object> view(int idx, String memberId);
    public MemberDTO selectMember(String memberId, String sessionId);
    public int registStudy(StudyDTO studyDTO, List<StudySubDTO> sharedList, FileDTO fileDTO);
    public int modifyStudy(StudyDTO studyDTO, List<StudySubDTO> sharedList, FileDTO fileDTO);
    public int deleteStudy(String idx, String sessionId);
    public int registShare(StudySubDTO sharedDTO);
    public void deleteShare(String idx);
    public void deleteLikes(String idx);
    public int likeStudy(String idx, String memberId, String memberName);
}
