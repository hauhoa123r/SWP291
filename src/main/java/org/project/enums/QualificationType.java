package org.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QualificationType {
    DEGREE("DEGREE"),
    CERTIFICATE("CERTIFICATE"),
    LICENSE("LICENSE"),
    AWARD("AWARD");

    private final String value;

}
