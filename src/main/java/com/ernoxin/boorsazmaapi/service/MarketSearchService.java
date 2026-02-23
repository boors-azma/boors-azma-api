package com.ernoxin.boorsazmaapi.service;

import java.util.List;
import java.util.Map;

public interface MarketSearchService {
    List<Map<String, Object>> search(String query);
}
