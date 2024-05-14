package org.fullstack4.projectstudywithme.service;

import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.fullstack4.projectstudywithme.dto.PageResponseDTO;
import org.fullstack4.projectstudywithme.dto.StudyDTO;

public interface StudyServiceIf {
    public PageResponseDTO<StudyDTO> list(PageRequestDTO pageRequestDTO);

}
