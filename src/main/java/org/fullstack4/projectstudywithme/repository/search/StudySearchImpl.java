package org.fullstack4.projectstudywithme.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.*;
import org.fullstack4.projectstudywithme.dto.PageRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Log4j2
public class StudySearchImpl extends QuerydslRepositorySupport implements StudySearch {
    public StudySearchImpl()
    {
        super(StudyEntity.class);
    }
    @Override
    public Page<StudyEntity> searchMyStudy(PageRequestDTO pageRequestDTO, String memberId) {
        String type = pageRequestDTO.getSearch_type();
        String search_word = pageRequestDTO.getSearch_word();
        String date1 = pageRequestDTO.getDate1();
        String date2 = pageRequestDTO.getDate2();
        PageRequest pageable = pageRequestDTO.getPageable(pageRequestDTO.getOrderType());
        QStudyEntity qStudy = QStudyEntity.studyEntity;
        JPQLQuery<StudyEntity> query = from(qStudy);
        query.where(qStudy.memberId.eq(memberId));
        if(type != null && !type.isEmpty()) {
            if(type.equals("t")) {
                query.where(qStudy.title.like("%" + search_word + "%"));
            }
            if(type.equals("c")) {
                query.where(qStudy.content.like("%" + search_word + "%"));
            }
        }
        if(date1 != null && !date1.isEmpty()) {
            query.where(qStudy.regDate.goe(LocalDate.parse(date1, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        if(date2 != null && !date2.isEmpty()) {
            query.where(qStudy.regDate.loe(LocalDate.parse(date2, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        query.where(qStudy.idx.gt(0));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);
        List<StudyEntity> studies = query.fetch();
        int total = (int)query. fetchCount();


        return new PageImpl<>(studies, pageable, total);
    }

    @Override
    public Page<StudyEntity> searchSharedFromMe(PageRequestDTO pageRequestDTO, String memberId) {
        String type = pageRequestDTO.getSearch_type();
        String search_word = pageRequestDTO.getSearch_word();
        String date1 = pageRequestDTO.getDate1();
        String date2 = pageRequestDTO.getDate2();
        PageRequest pageable = pageRequestDTO.getPageable(pageRequestDTO.getOrderType());
        QStudyEntity qStudy = QStudyEntity.studyEntity;
        JPQLQuery<StudyEntity> query = from(qStudy);
        query.where(qStudy.memberId.eq(memberId));
        query.where(qStudy.displayYn.eq("Y"));
        if(type != null && !type.isEmpty()) {
            if(type.equals("t")) {
                query.where(qStudy.title.like("%" + search_word + "%"));
            }
            if(type.equals("c")) {
                query.where(qStudy.content.like("%" + search_word + "%"));
            }
        }
        if(date1 != null && !date1.isEmpty()) {
            query.where(qStudy.regDate.goe(LocalDate.parse(date1, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        if(date2 != null && !date2.isEmpty()) {
            query.where(qStudy.regDate.loe(LocalDate.parse(date2, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        query.where(qStudy.idx.gt(0));
        //paging
        this.getQuerydsl().applyPagination(pageable, query);
        log.info("query : {}", query);
        List<StudyEntity> studies = query.fetch();
        int total = (int)query. fetchCount();
        return new PageImpl<>(studies, pageable, total);
    }

    @Override
    public Page<StudyEntity> searchSharedToMe(PageRequestDTO pageRequestDTO, String memberId) {
        String type = pageRequestDTO.getSearch_type();
        String search_word = pageRequestDTO.getSearch_word();
        String date1 = pageRequestDTO.getDate1();
        String date2 = pageRequestDTO.getDate2();
        PageRequest pageable = pageRequestDTO.getPageable(pageRequestDTO.getOrderType());
        QStudyEntity qStudy = QStudyEntity.studyEntity;
        QSharedEntity qShared = QSharedEntity.sharedEntity;
        JPQLQuery<StudyEntity> query = from(qStudy);
        query.join(qShared);
        query.on(qShared.studyIdx.eq(qStudy.idx));
        query.where(qStudy.displayStartDate.goe(LocalDate.now()));
        query.where(qStudy.displayEndDate.loe(LocalDate.now()));
        query.where(qShared.memberId.eq(memberId));
        if(type != null && !type.isEmpty()) {
            if(type.equals("t")) {
                query.where(qStudy.title.like("%" + search_word + "%"));
            }
            if(type.equals("c")) {
                query.where(qStudy.content.like("%" + search_word + "%"));
            }
        }
        if(date1 != null && !date1.isEmpty()) {
            query.where(qStudy.regDate.goe(LocalDate.parse(date1, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        if(date2 != null && !date2.isEmpty()) {
            query.where(qStudy.regDate.loe(LocalDate.parse(date2, DateTimeFormatter.ISO_DATE).atStartOfDay()));
        }
        query.where(qStudy.idx.gt(0));
        //paging
        this.getQuerydsl().applyPagination(pageable, query);
        log.info("query : {}", query);
        List<StudyEntity> studies = query.fetch();
        int total = (int)query. fetchCount();
        return new PageImpl<>(studies, pageable, total);
    }
}
