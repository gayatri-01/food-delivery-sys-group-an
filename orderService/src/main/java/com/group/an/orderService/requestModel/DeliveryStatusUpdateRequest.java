package com.group.an.orderService.requestModel;

import com.group.an.dataService.models.DeliveryStatus;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatusUpdateRequest {
    private int orderId;
    private int personnelId;
    private DeliveryStatus deliveryStatus;
}

