package com.cfgtest.services.customerservice.dto;


import lombok.Data;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@Data
public class ExtendedFields {
// Version/Id cannot be defined within @Embeddable class..
// embeddables are part of the entity (as the name suggests they are embedded)
// and thus independent versioning doesn't make sense.
//    @Version
//    private Integer version;

    private Timestamp createDate;

    private Timestamp updateDate;

    private boolean logicalDeleteIndicator;
}
