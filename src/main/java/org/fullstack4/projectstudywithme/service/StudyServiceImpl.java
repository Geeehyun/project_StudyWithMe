package org.fullstack4.projectstudywithme.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.fullstack4.projectstudywithme.dto.PageResponseDTO;
import org.fullstack4.projectstudywithme.dto.StudyDTO;
import org.fullstack4.projectstudywithme.repository.StudyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyServiceIf {
    private final StudyRepository studyRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<StudyDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getSearch_types();
        String search_word = pageRequestDTO.getSearch_word();
        PageRequest pageable = pageRequestDTO.getPageable("idx");
        Page<StudyEntity> result = studyRepository.search(pageable, types, search_word);

        List<StudyDTO> dtoList = result.getContent().stream()
                .map(entity->modelMapper.map(entity, StudyDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<StudyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total_count((int)result.getTotalElements())
                .build();
    }
}
