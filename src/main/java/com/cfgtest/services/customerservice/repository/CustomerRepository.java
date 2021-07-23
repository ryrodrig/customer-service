package com.cfgtest.services.customerservice.repository;

import com.cfgtest.services.customerservice.dto.CustomerDTO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Customer Repository associated with "Customer" table.
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<CustomerDTO, UUID> {

    CustomerDTO findBySsn(String ssn);

}
