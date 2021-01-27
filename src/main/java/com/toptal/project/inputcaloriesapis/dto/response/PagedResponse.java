package com.toptal.project.inputcaloriesapis.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {

    private Integer totalPages;

    private Long totalCount;

    private Integer pageNo;

    private Integer pageSize;

    List<T> data;
}
