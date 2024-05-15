package org.fullstack4.projectstudywithme.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.LikeEntity;
import org.fullstack4.projectstudywithme.domain.MemberEntity;
import org.fullstack4.projectstudywithme.domain.SharedEntity;
import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.fullstack4.projectstudywithme.dto.*;
import org.fullstack4.projectstudywithme.repository.MemberRepository;
import org.fullstack4.projectstudywithme.repository.StudyRepository;
import org.fullstack4.projectstudywithme.repository.search.LikeRepository;
import org.fullstack4.projectstudywithme.repository.search.SharedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyServiceIf {
    private final StudyRepository studyRepository;
    private final SharedRepository sharedRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<StudyDTO> list(PageRequestDTO pageRequestDTO, String memberId) {
        Page<StudyEntity> result = studyRepository.searchMyStudy(pageRequestDTO, memberId);

        List<StudyDTO> dtoList = result.getContent().stream()
                .map(entity->modelMapper.map(entity, StudyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<StudyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }

    @Override
    public Map<String, Object> view(int idx, String memberID) {
        Map<String, Object> map = new HashMap<>();
        StudyDTO studyDTO = new StudyDTO();
        List<StudySubDTO> sharedDTOList = null;
        List<StudySubDTO> likedDTOList = null;

        StudyEntity studyEntity = studyRepository.findAllByIdx(idx);
        List<SharedEntity> sharedEntityList = sharedRepository.findAllByStudyIdx(idx);
        List<LikeEntity> likeEntityList = likeRepository.findAllByStudyIdx(idx);
        if (studyEntity != null) {
            if(studyEntity.getMemberId().equals(memberID)) {
                studyDTO = modelMapper.map(studyEntity, StudyDTO.class);
                studyDTO.setDate();
                studyDTO.setCategories();
                if(sharedEntityList != null) {
                    sharedDTOList = sharedEntityList.stream().map(entity -> modelMapper.map(entity, StudySubDTO.class)).toList();
                    sharedDTOList.forEach(StudySubDTO::setDate);
                }
                if(likeEntityList != null) {
                    likedDTOList = likeEntityList.stream().map(entity -> modelMapper.map(entity, StudySubDTO.class)).toList();
                    likedDTOList.forEach(StudySubDTO::setDate);
                }
            } else {
                studyDTO.setMessage("본인의 게시글 또는 공유받은 게시글만 조회할 수 있습니다.");
            }
        } else {
            studyDTO.setMessage("없는 게시글 입니다.");
        }
        map.put("studyDTO", studyDTO);
        map.put("sharedDTOList", sharedDTOList);
        map.put("likedDTOList", likedDTOList);
        return map;
    }

    @Override
    public MemberDTO selectMember(String memberId, String sessionId) {
        MemberDTO memberDTO = new MemberDTO();
        MemberEntity memberEntity = memberRepository.findAllByMemberIdAndStatusEquals(memberId, "Y");
        if (memberEntity != null) {
            if (memberEntity.getMemberId().equals(sessionId)) {
                memberDTO.setMessage("작성자 본인 조회");
            } else {
                memberDTO = modelMapper.map(memberEntity, MemberDTO.class);
            }
        } else {
            memberDTO.setMessage("회원정보 없음");
        }
        return memberDTO;
    }

    @Override
    public int registStudy(StudyDTO studyDTO) {
        StudyEntity studyEntity = modelMapper.map(studyDTO, StudyEntity.class);
        int idx = studyRepository.save(studyEntity).getIdx();
        return idx;
    }

    @Override
    public int registShare(StudySubDTO sharedDTO) {
        SharedEntity sharedEntity = modelMapper.map(sharedDTO, SharedEntity.class);
        int idx = sharedRepository.save(sharedEntity).getIdx();
        return idx;
    }
}
