package org.project.admin.dto.request;


import lombok.Data;

@Data
public class LogSearchRequest {
    private Long entityId;         // ID của entity liên quan (userId, staffId, ...)
    private String action;         // CREATE, UPDATE, DELETE
    private String logTimeFrom;
    private String logTimeTo;
    private String keyword;        // Tìm kiếm trong logDetail
}
