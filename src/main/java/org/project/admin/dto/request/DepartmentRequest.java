package org.project.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String videoUrl;
    private String bannerUrl;
    private String slogan;

//    @NotNull
//    private Long managerId;

}
