package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffEducationResponse {
    private Long id;
    private Integer year;
    private String degree;
    private String institute;
    private String result;
}
