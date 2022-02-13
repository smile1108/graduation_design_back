package com.jiac.common.vo;

import com.jiac.common.dto.BacklogDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * FileName: BacklogVo
 * Author: Jiac
 * Date: 2022/2/9 11:55
 */
@Data
public class BacklogVo {
    private String id;

    private String title;

    private Boolean done;

    private String username;

    public static BacklogVo of(BacklogDto backlogDto) {
        BacklogVo backlogVo = new BacklogVo();
        BeanUtils.copyProperties(backlogDto, backlogVo);
        backlogVo.setUsername(backlogDto.getUser().getUsername());
        return backlogVo;
    }
}
