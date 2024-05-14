package org.fullstack4.projectstudywithme.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.domain.QStudyEntity;
import org.fullstack4.projectstudywithme.domain.StudyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class StudySearchImpl extends QuerydslRepositorySupport implements StudySearch {
    public StudySearchImpl()
    {
        super(StudyEntity.class);
    }
    @Override
    public Page<StudyEntity> search(Pageable pageable, String[] types, String search_word) {
        QStudyEntity qStudy = QStudyEntity.studyEntity;
        JPQLQuery<StudyEntity> query = from(qStudy);

        if((types != null && types.length > 0) && (search_word != null && search_word.length() > 0)) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
//            type : t-제목, c- 내용, u- 사용자 아이디
            for (String type : types) {
                switch (type) {
                    case "t" :
                        booleanBuilder.or(qStudy.title.like("%"+search_word+"%"));
                        break;
                    case "c" :
                        booleanBuilder.or(qStudy.content.like("%"+search_word+"%"));
                        break;
                    case "u" :
                        booleanBuilder.or(qStudy.memberId.like("%"+search_word+"%"));
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(qStudy.idx.gt(0));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        log.info("query : {}", query);
        List<StudyEntity> studies = query.fetch();
        int total = (int)query. fetchCount();


        log.info("studies : {}", studies);
        log.info("total : {}", total);
        log.info("BoardSearchImpl >> search End");
        log.info("========================================");
        return new PageImpl<>(studies, pageable, total);
    }
}
