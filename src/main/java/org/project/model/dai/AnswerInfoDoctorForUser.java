package org.project.model.dai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInfoDoctorForUser {
    private Long id;
    private String nameDoctor;
    private String nameDepartment;
    private String phoneNumber;
    private String hospitalName;
}
