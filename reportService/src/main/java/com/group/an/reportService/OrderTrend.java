package com.group.an.reportService;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTrend {
    private Integer orderCount;
    private Double totalRevenue;
    private Double averageOrderValue;
}
