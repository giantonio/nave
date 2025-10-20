package com.nave.spring356.nave.models.responses;

import lombok.Builder;

@Builder
public record ParamErrorResponse(
    String field,
    String message
) {
    
}
