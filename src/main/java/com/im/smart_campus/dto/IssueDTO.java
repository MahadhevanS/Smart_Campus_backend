package com.im.smart_campus.dto;

import com.im.smart_campus.entity.Enums;
import lombok.Data;

@Data
public class IssueDTO {
    private String title;
    private String description;
    private String location;
    private Enums.IssueCategory category;
    private String imageUrl;
}