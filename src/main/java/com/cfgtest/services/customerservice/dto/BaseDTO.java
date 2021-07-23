package com.cfgtest.services.customerservice.dto;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class BaseDTO {

    // Since @Version cannot be defined within @Embeddable it needs to be a part of BaseDTO.
    @Version
    private Integer version;
}
