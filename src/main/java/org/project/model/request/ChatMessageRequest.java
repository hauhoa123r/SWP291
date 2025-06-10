package org.project.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {
    @JsonProperty(value = "id")
    private Long userId;
    @NotBlank
    private String userMessage;

    private String prompt;

    private String language;
}
