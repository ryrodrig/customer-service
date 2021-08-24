package com.cfgtest.services.customerservice.dto;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Embeddable
@Data
public class ExtendedFields {
// Version/Id cannot be defined within @Embeddable class..
// embeddables are part of the entity (as the name suggests they are embedded)
// and thus independent versioning doesn't make sense.
//    @Version
//    private Integer version;

// CreationTimestamp is not yet implemented in embeddable classes hence we will be using
// @PrePersist function as below
//    @CreationTimestamp
    private LocalDateTime createDate;

// UpdateTimestamp is not yet implemented in embeddable classes hence we will be using
// @PrePersist function as below
//    @UpdateTimestamp
    private LocalDateTime updateDate;

    private Boolean logicalDeleteIndicator;

    // Invoked before save.
    @PrePersist
    public void prePersist() {
        createDate = LocalDateTime.now();
        logicalDeleteIndicator = false;
    }

    // Invoked before update.
    @PreUpdate
    public void preUpdate() {
        updateDate = LocalDateTime.now();
    }
}
