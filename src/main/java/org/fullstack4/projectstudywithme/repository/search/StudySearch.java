package org.fullstack4.projectstudywithme.repository.search;

import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudySearch {
    Page<StudyEntity> searchMyStudy(PageRequestDTO pageRequestDTO, String memberId);
    Page<StudyEntity> searchSharedFromMe(Pageable pageable, String[] types, String search_word, String memberId);
    Page<StudyEntity> searchSharedToMe(Pageable pageable, String[] types, String search_word, String memberId);
}
