package org.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StaffType {
    PART_TIME_CONTRACT("part_time_contract"),
    INTERN("intern"),
    CONSULTANT("consultant"),
    FULL_TIME("full_time");

    private final String value;
}
