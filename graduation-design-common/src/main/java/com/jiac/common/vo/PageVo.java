package com.jiac.common.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * FileName: PageVo
 * Author: Jiac
 * Date: 2022/2/16 8:04
 */
@Data
public class PageVo<T> {
    private List<T> lists;
    private Long count; // 代表满足条件的总共条数
    private Integer sumPage;  // 代表总共页数
}
