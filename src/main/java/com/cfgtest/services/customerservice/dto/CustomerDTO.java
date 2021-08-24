package com.cfgtest.services.customerservice.dto;


import com.cfgtest.services.customerservice.utils.CFGCheck;
import com.cfgtest.services.customerservice.utils.OnCreate;
import com.cfgtest.services.customerservice.utils.OnUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.UUID;

// a class annotated with @Entity are proxied at run time and the proxy classes maintain
// state of if the entity was updated/touched
// The classes should not be final
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CustomerDTO extends BaseDTO {


    // generate primary key automatically
    // AUTO/IDENTITY/SEQUENCE/TABLE - 4 generation types.
//    @GeneratedValue(generator = "customerIdGenerator")
//    Hibernate Annotation to generate a value (Custom Generator)
//    @GenericGenerator(name="customerIdGenerator", strategy = "org.hibernate.id.UUIDGenerator")
    // Strategy can be a custom class that implements IdentifierGenerator interface
//    Needed to fix an issue with mapping UUID to object.
//    @Type(type="uuid-char")
//    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)


    // Interesting feature with Hibernate 5 , this will  generate a UUID value.. No need of the above
    // Indicates a primary key
    @Id
    @GeneratedValue
    // Custom interfaces defined that can be used as groups
    @Null(groups = OnCreate.class, message = "Customer ID should be null during create.")
    @NotNull(groups = OnUpdate.class, message = "Customer ID cannot be null.")
    private UUID id;

    private String firstName;

    private String middleName;

    private String lastName;

    @Past
    private Timestamp dateOfBirth;

    @Email
    private String email;

//    @Min(10)
//    @Max(12)
    @Column(length = 12, columnDefinition = "varchar")
    private String ssn;

    private String phone;

    @Embedded
//    Attribute overrides can be used to define column names.
//    @AttributeOverrides({
//            @AttributeOverride( name = "firstName", column = @Column(name = "contact_first_name")),
//            @AttributeOverride( name = "lastName", column = @Column(name = "contact_last_name")),
//            @AttributeOverride( name = "phone", column = @Column(name = "contact_phone"))
//    })
    private CustomerAddress address;

    private String university;

    private Boolean employed;

    // Custom constraint validator.
    @CFGCheck
    private String employerName;

    @Positive
    private Integer salary;

    @Embedded
    private ExtendedFields extendedFields = new ExtendedFields();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    // annotation does not create a new table but is part of the table the class is a instance variable of.
    @Embeddable
    public static class CustomerAddress {

        private String line1;

        private String line2;

        private String zipcode;
    }

}
