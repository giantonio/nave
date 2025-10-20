package com.nave.spring356.nave.models.dtos;

import java.util.List;
import java.util.function.Function;

public record PageDto<T>(List<T> content) {
    public <U> PageDto<U> map(Function<T, U> converter){
        return new PageDto<U>(
                content.stream().map(converter).toList()
        );
    }
}
