package org.fullstack4.projectstudywithme.repository.search;

import org.fullstack4.projectstudywithme.domain.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository  extends JpaRepository<LikeEntity, String> {
    List<LikeEntity> findAllByStudyIdx(int studyIdx);
}
