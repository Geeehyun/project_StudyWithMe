package org.fullstack4.projectstudywithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.Common.FileUtil;
import org.fullstack4.projectstudywithme.domain.LikeEntity;
import org.fullstack4.projectstudywithme.domain.MemberEntity;
import org.fullstack4.projectstudywithme.domain.SharedEntity;
import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.fullstack4.projectstudywithme.dto.*;
import org.fullstack4.projectstudywithme.repository.MemberRepository;
import org.fullstack4.projectstudywithme.repository.StudyRepository;
import org.fullstack4.projectstudywithme.repository.LikeRepository;
import org.fullstack4.projectstudywithme.repository.SharedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        dtoList.forEach(dto -> {dto.setDate();dto.setCategories();dto.setTagsArr();});

        return PageResponseDTO.<StudyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<StudyDTO> listFromMe(PageRequestDTO pageRequestDTO, String memberId) {
        Page<StudyEntity> result = studyRepository.searchSharedFromMe(pageRequestDTO, memberId);
        List<StudyDTO> dtoList = result.getContent().stream()
                .map(entity->modelMapper.map(entity, StudyDTO.class))
                .collect(Collectors.toList());
        dtoList.forEach(dto -> {dto.setDate();dto.setCategories();dto.setTagsArr();});

        return PageResponseDTO.<StudyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<StudyDTO> listToMe(PageRequestDTO pageRequestDTO, String memberId) {
        Page<StudyEntity> result = studyRepository.searchSharedToMe(pageRequestDTO, memberId);
        List<StudyDTO> dtoList = result.getContent().stream()
                .map(entity->modelMapper.map(entity, StudyDTO.class))
                .collect(Collectors.toList());
        dtoList.forEach(dto -> {dto.setDate();dto.setCategories();dto.setTagsArr();});

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
                studyDTO.setTagsArr();
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
    public Map<String, Object> viewForShare(int idx, String sharedMemberId) {
        Map<String, Object> map = new HashMap<>();
        StudyDTO studyDTO = new StudyDTO();
        List<StudySubDTO> sharedDTOList = null;
        List<StudySubDTO> likedDTOList = null;
        StudyEntity studyEntity = studyRepository.findAllByIdx(idx);
        List<SharedEntity> sharedEntityList = sharedRepository.findAllByStudyIdx(idx);
        List<String> sharedMemberList = new ArrayList<>();
        sharedEntityList.forEach(dto -> {sharedMemberList.add(dto.getMemberId());});
        List<LikeEntity> likeEntityList = likeRepository.findAllByStudyIdx(idx);
        if (studyEntity != null) {
            if(sharedMemberList.contains(sharedMemberId)) {
                studyDTO = modelMapper.map(studyEntity, StudyDTO.class);
                studyDTO.setDate();
                studyDTO.setCategories();
                studyDTO.setTagsArr();
                if(sharedEntityList != null) {
                    sharedDTOList = sharedEntityList.stream().map(entity -> modelMapper.map(entity, StudySubDTO.class)).toList();
                    sharedDTOList.forEach(StudySubDTO::setDate);
                }
                if(likeEntityList != null) {
                    likedDTOList = likeEntityList.stream().map(entity -> modelMapper.map(entity, StudySubDTO.class)).toList();
                    likedDTOList.forEach(StudySubDTO::setDate);
                }
            } else {
                studyDTO.setMessage("공유 받은 게시글만 조회할 수 있습니다.");
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

    @Transactional
    @Override
    public int registStudy(StudyDTO studyDTO, List<StudySubDTO> sharedList, FileDTO fileDTO) {
        int result = 0;
        StudyEntity studyEntity = null;
        if(fileDTO != null) {
            Map<String, String> map = FileUtil.FileUpload(fileDTO);
            if (map.get("result").equals("success")) {
                studyDTO.setThumbnailPath("/upload/");
                studyDTO.setThumbnail(map.get("newName"));
                studyEntity= modelMapper.map(studyDTO, StudyEntity.class);
                int idx = studyRepository.save(studyEntity).getIdx();
                sharedList.forEach(dto -> {dto.setStudyIdx(String.valueOf(idx));});
                for(StudySubDTO dto : sharedList) {
                    int subIdx = registShare(dto);
                    if (subIdx > 0) {
                        result++;
                    }
                }
            }
        } else {
            studyEntity= modelMapper.map(studyDTO, StudyEntity.class);
            int idx = studyRepository.save(studyEntity).getIdx();
            sharedList.forEach(dto -> {dto.setStudyIdx(String.valueOf(idx));});
            for(StudySubDTO dto : sharedList) {
                int subIdx = registShare(dto);
                if (subIdx > 0) {
                    result++;
                }
            }
        }
        return result;
    }

    @Transactional
    @Override
    public int modifyStudy(StudyDTO studyDTO, List<StudySubDTO> sharedList, FileDTO fileDTO) {
        int result = 0;
        StudyEntity studyEntity = null;
        Map<String, Object> orgMap = view(Integer.parseInt(studyDTO.getIdx()), studyDTO.getMemberId());
        StudyDTO orgStudyDTO = (StudyDTO) orgMap.get("studyDTO");
        List<StudySubDTO> orgSharedDTOList = (List<StudySubDTO>) orgMap.get("sharedDTOList");
        List<String> newMemberList = new ArrayList<>();
        List<String> orgMemberList = new ArrayList<>();
        log.info("orgStudyDTO : {}", orgStudyDTO);
        if(fileDTO != null) {
            Map<String, String> map = FileUtil.FileUpload(fileDTO);
            if (map.get("result").equals("success")) {
                studyDTO.setThumbnailPath("/upload/");
                studyDTO.setThumbnail(map.get("newName"));
                studyEntity= modelMapper.map(studyDTO, StudyEntity.class);
                int idx = studyRepository.save(studyEntity).getIdx();
                result = idx;
                sharedList.forEach(dto -> {dto.setStudyIdx(String.valueOf(idx));});
                sharedList.forEach(dto -> {newMemberList.add(dto.getMemberId());});
                orgSharedDTOList.forEach(dto -> {orgMemberList.add(dto.getMemberId());});
                // 있던건데 없어지면 삭제
                List<String> deleteIdx = new ArrayList<>();
                for(StudySubDTO orgDTO : orgSharedDTOList) {
                    if(!newMemberList.contains(orgDTO.getMemberId())){
                        deleteIdx.add(orgDTO.getIdx());
                    }
                }
                for(String dIdx : deleteIdx){
                    deleteShare(dIdx);
                }
                // 초면이면 추가 아니면 무시
                for(StudySubDTO newDTO : sharedList) {
                    if(!orgMemberList.contains(newDTO.getMemberId())){
                        int subIdx = registShare(newDTO);
                    }
                }
            }
        } else {
            studyDTO.setThumbnailPath(orgStudyDTO.getThumbnailPath());
            studyDTO.setThumbnail(orgStudyDTO.getThumbnail());
            log.info("studyDTO : {}", studyDTO);
            studyEntity= modelMapper.map(studyDTO, StudyEntity.class);
            int idx = studyRepository.save(studyEntity).getIdx();
            result = idx;
            sharedList.forEach(dto -> {dto.setStudyIdx(String.valueOf(idx));});
            sharedList.forEach(dto -> {newMemberList.add(dto.getMemberId());});
            orgSharedDTOList.forEach(dto -> {orgMemberList.add(dto.getMemberId());});
            // 있던건데 없어지면 삭제
            List<String> deleteIdx = new ArrayList<>();
            for(StudySubDTO orgDTO : orgSharedDTOList) {
                if(!newMemberList.contains(orgDTO.getMemberId())){
                    deleteIdx.add(orgDTO.getIdx());
                }
            }
            for(String dIdx : deleteIdx){
                deleteShare(dIdx);
            }
            // 초면이면 추가 아니면 무시
            for(StudySubDTO newDTO : sharedList) {
                if(!orgMemberList.contains(newDTO.getMemberId())){
                    int subIdx = registShare(newDTO);
                }
            }
        }
        return result;
    }

    @Transactional
    @Override
    public int deleteStudy(String idx, String sessionId) {
        log.info("====================================== deleteStudy START");
        int result = 0;
        Map<String, Object> orgMap = view(Integer.parseInt(idx), sessionId);
        StudyDTO studyDTO = (StudyDTO) orgMap.get("studyDTO");
        log.info("studyDTO : {}", studyDTO);
        if(studyDTO.getMessage() == null) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 삭제 로직 시작");
            studyRepository.deleteById(studyDTO.getIdx());
            sharedRepository.deleteAllByStudyIdx(Integer.parseInt(studyDTO.getIdx()));
            likeRepository.deleteAllByStudyIdx(Integer.parseInt(studyDTO.getIdx()));
            result = 1;
        }
        log.info("====================================== deleteStudy END");
        return result;
    }

    @Override
    public int registShare(StudySubDTO sharedDTO) {
        SharedEntity sharedEntity = modelMapper.map(sharedDTO, SharedEntity.class);
        int idx = sharedRepository.save(sharedEntity).getIdx();
        return idx;
    }

    @Override
    public void deleteShare(String idx) {
        sharedRepository.deleteById(idx);
    }

    @Override
    public void deleteLikes(String idx) {
        likeRepository.deleteById(idx);
    }

    @Override
    public int likeStudy(String idx, String memberId, String memberName) {
        LikeEntity likeEntity = LikeEntity.builder()
                .studyIdx(Integer.parseInt(idx))
                .memberId(memberId)
                .memberName(memberName)
                .build();
        int result = likeRepository.save(likeEntity).getIdx();
        if(result > 0) {
            Map<String, Object> orgMap = view(Integer.parseInt(idx), memberId);
            StudyDTO studyDTO = (StudyDTO) orgMap.get("studyDTO");
            studyDTO.setLikePlus1();
            StudyEntity studyEntity = modelMapper.map(studyDTO, StudyEntity.class);
            studyRepository.save(studyEntity);
        }
        return result;
    }

    @Override
    public int likeStudy2(String idx, String memberId, String memberName) {
        LikeEntity likeEntity = LikeEntity.builder()
                .studyIdx(Integer.parseInt(idx))
                .memberId(memberId)
                .memberName(memberName)
                .build();
        int result = likeRepository.save(likeEntity).getIdx();
        if(result > 0) {
            Map<String, Object> orgMap = viewForShare(Integer.parseInt(idx), memberId);
            StudyDTO studyDTO = (StudyDTO) orgMap.get("studyDTO");
            studyDTO.setLikePlus1();
            StudyEntity studyEntity = modelMapper.map(studyDTO, StudyEntity.class);
            studyRepository.save(studyEntity);
        }
        return result;
    }
}
