package org.fullstack4.projectstudywithme.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.QStudyEntity;
import org.fullstack4.projectstudywithme.domain.StudyEntity;
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
    public Page<StudyEntity> searchMyStudy(PageRequestDTO pageRequestDTO, String member_id) {
        String type = pageRequestDTO.getSearch_type();
        String search_word = pageRequestDTO.getSearch_word();
        String date1 = pageRequestDTO.getDate1();
        String date2 = pageRequestDTO.getDate2();
        PageRequest pageable = pageRequestDTO.getPageable(pageRequestDTO.getOrderType());
        QStudyEntity qStudy = QStudyEntity.studyEntity;
        JPQLQuery<StudyEntity> query = from(qStudy);
        query.where(qStudy.memberId.eq(member_id));
        if(type != null && !type.isEmpty()) {
            if(type.equals("t")) {
                query.where(qStudy.title.like("%" + search_word + "%"));
            }
            if(type.equals("c")) {
                query.where(qStudy.content.like("%" + search_word + "%"));
            }
        }
//        if((types != null && types.length > 0) && (search_word != null && search_word.length() > 0)) {
//            BooleanBuilder booleanBuilder = new BooleanBuilder();
////            type : t-제목, c- 내용, u- 사용자 아이디
//            for (String type : types) {
//                switch (type) {
//                    case "t" :
//                        booleanBuilder.or(qStudy.title.like("%"+search_word+"%"));
//                        break;
//                    case "c" :
//                        booleanBuilder.or(qStudy.content.like("%"+search_word+"%"));
//                        break;
////                    case "u" :
////                        booleanBuilder.or(qStudy.memberId.like("%"+search_word+"%"));
////                        break;
//                }
//            }
//            query.where(booleanBuilder);
//        }
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
    public Page<StudyEntity> searchSharedFromMe(Pageable pageable, String[] types, String search_word, String memberId) {
        return null;
    }

    @Override
    public Page<StudyEntity> searchSharedToMe(Pageable pageable, String[] types, String search_word, String memberId) {
        return null;
    }
}
