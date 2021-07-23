package com.cfgtest.services.customerservice.api.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class DateMapper {

    public LocalDate convertToLocalDate(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                            .map(ts -> LocalDate.of(
                                    timestamp.toLocalDateTime().getYear(),
                                    timestamp.toLocalDateTime().getMonth(),
                                    timestamp.toLocalDateTime().getDayOfMonth()))
                            .orElse(null);
    }


    public Timestamp convertToTimestamp(LocalDate localDate) {
        return Optional.of(localDate)
                    .map(ld -> Timestamp.valueOf(
                            localDate.atTime(LocalTime.MIDNIGHT)))
                    .orElse(null);
    }

}
