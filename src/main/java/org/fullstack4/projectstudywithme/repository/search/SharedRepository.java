package org.fullstack4.projectstudywithme.repository.search;

import org.fullstack4.projectstudywithme.domain.SharedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharedRepository extends JpaRepository<SharedEntity, String> {
    List<SharedEntity> findAllByStudyIdx(int studyIdx);
}
