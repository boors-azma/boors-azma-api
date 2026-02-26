package com.ernoxin.boorsazmaapi.dto;

public record WatchlistSymbolResponse(
        Long id,
        String symbolKey,
        String symbol,
        String name,
        String sourceType,
        String instrumentCode,
        String isin
) {
}
