package com.ernoxin.boorsazmaapi.controller;

import com.ernoxin.boorsazmaapi.dto.api.ApiResponse;
import com.ernoxin.boorsazmaapi.service.MarketSearchService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/market-search")
@RequiredArgsConstructor
@Validated
public class MarketSearchController {

    private final MarketSearchService marketSearchService;

    @GetMapping("/symbols")
    public ApiResponse<List<Map<String, Object>>> search(
            @RequestParam("query") @NotBlank(message = "عبارت جستجو نباید خالی باشد.") String query
    ) {
        return ApiResponse.of(HttpStatus.OK, "عملیات با موفقیت انجام شد", marketSearchService.search(query));
    }
}
