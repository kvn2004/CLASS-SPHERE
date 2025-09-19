package lk.vihanganimsara.classsphere.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@Data
public class PayHereRequestDto {
    private String orderId;
    private BigDecimal amount;
    private String currency;
}
