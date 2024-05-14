package org.fullstack4.projectstudywithme.repository;

import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.fullstack4.projectstudywithme.repository.search.StudySearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<StudyEntity, String>, StudySearch {
}
