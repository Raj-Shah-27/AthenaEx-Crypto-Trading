package com.rns2706.athenaex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketChartData {
    private long timestamp;
    private double price;
}
