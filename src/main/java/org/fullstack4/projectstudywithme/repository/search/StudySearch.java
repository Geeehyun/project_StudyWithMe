package org.fullstack4.projectstudywithme.repository.search;

import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudySearch {
    Page<StudyEntity> search(Pageable pageable, String[] types, String search_word);
}
