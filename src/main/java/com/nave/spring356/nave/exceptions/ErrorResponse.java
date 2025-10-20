package com.nave.spring356.nave.exceptions;

import lombok.Builder;

public record ErrorResponse (    
    int status,
    String error,
    String message,
    String path
){    
}
