package lk.vihanganimsara.classsphere.controller;

import lk.vihanganimsara.classsphere.dto.PayHereRequestDto;
import lk.vihanganimsara.classsphere.dto.PaymentRequestDto;
import lk.vihanganimsara.classsphere.entity.Payment;
import lk.vihanganimsara.classsphere.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class PayHereController {

    private static final Logger log = LoggerFactory.getLogger(PayHereController.class);
    @Value("${payhere.merchant.id}")
    private String merchantId;

    @Value("${payhere.merchant.secret}")
    private String merchantSecret;

    @PostMapping("/generate-hash")
    public Map<String, String> generateHash(@RequestBody PayHereRequestDto dto) {
        // Make sure amount has 2 decimals
        String amount = dto.getAmount().setScale(2, RoundingMode.HALF_UP).toString();

        String toHash = merchantId + dto.getOrderId() + amount + dto.getCurrency() + merchantSecret;
        String hash = DigestUtils.sha256Hex(toHash).toUpperCase();

        Map<String, String> response = new HashMap<>();
        response.put("merchantId", merchantId);
        response.put("hash", hash);
        log.info("Merchant Id: " + merchantId);
        log.info("Merchant Secret: " + merchantSecret);
       log.info("hash" +hash);
        return response;
    }

    // Handle PayHere notification
    @PostMapping("/notify")
    public ResponseEntity<String> handleNotification(@RequestParam Map<String, String> params) {
        System.out.println("PayHere Notification: " + params);
        // TODO: Save payment status in DB
        return ResponseEntity.ok("Notification received");
    }
}