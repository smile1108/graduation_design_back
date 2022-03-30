package com.jiac.common.dto;

import lombok.Data;

/**
 * FileName: FrontMessageDto
 * Author: Jiac
 * Date: 2022/3/29 13:06
 */
@Data
public class FrontMessageDto {
    private String from;
    private String to;
    private String content;
    private String type;
}
