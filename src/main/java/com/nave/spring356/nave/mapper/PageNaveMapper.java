package com.nave.spring356.nave.mapper;

import org.springframework.data.domain.Page;

import com.nave.spring356.nave.models.dtos.PageDto;

public class PageNaveMapper<T> {

    public PageDto<T> entityToDto(final Page<T> page) {       
        return new PageDto<T>(
                page.getContent()
        );
    }

    

    
}
