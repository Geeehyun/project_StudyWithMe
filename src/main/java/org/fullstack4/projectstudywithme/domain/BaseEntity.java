package org.fullstack4.projectstudywithme.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {
    @CreatedDate
    @Column( updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(updatable = true, nullable = true, insertable = false)
    private LocalDateTime modifyDate;

    public void setModifyDate(LocalDateTime modify_date) {
        this.modifyDate = LocalDateTime.now();
    }
}
