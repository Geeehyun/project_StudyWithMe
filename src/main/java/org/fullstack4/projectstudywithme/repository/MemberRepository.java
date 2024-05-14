package org.fullstack4.projectstudywithme.repository;

import org.fullstack4.projectstudywithme.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    MemberEntity findAllByMemberId(String memberID);
    MemberEntity findAllByMemberIdAndStatusEquals(String memberId, String status);
}
