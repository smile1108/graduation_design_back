package com.jiac.common.dto;

import com.jiac.common.entity.Backlog;
import com.jiac.common.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * FileName: BacklogDto
 * Author: Jiac
 * Date: 2022/2/9 11:54
 */
@Data
public class BacklogDto {
    private String id;

    private String title;

    private Boolean done;

    private User user;

    public static BacklogDto of(Backlog backlog) {
        BacklogDto backlogDto = new BacklogDto();
        BeanUtils.copyProperties(backlog, backlogDto);
        return backlogDto;
    }
}
