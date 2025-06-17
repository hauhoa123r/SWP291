package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.enums.QualificationType;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffExperienceResponse {
    private Long id;
    private String title;
    private String description;
    private Date issueDate;
    private Date expirationDate;
    private String url;
    private QualificationType qualificationType;
}
