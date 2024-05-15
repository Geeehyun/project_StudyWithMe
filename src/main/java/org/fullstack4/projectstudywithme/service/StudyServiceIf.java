package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import org.fullstack4.projectstudywithme.dto.*;

import java.util.Map;

public interface StudyServiceIf {
    public PageResponseDTO<StudyDTO> list(PageRequestDTO pageRequestDTO, String memberId);
    public Map<String, Object> view(int idx, String memberId);
    public MemberDTO selectMember(String memberId, String sessionId);
    public int registStudy(StudyDTO studyDTO);
    public int registShare(StudySubDTO sharedDTO);
}
