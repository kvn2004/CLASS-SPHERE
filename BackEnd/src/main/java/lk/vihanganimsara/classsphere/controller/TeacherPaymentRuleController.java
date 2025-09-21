package lk.vihanganimsara.classsphere.controller;

import lk.vihanganimsara.classsphere.entity.TeacherPaymentRule;
import lk.vihanganimsara.classsphere.service.TeacherPaymentRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher-payment-rules")
@RequiredArgsConstructor
public class TeacherPaymentRuleController {

    private final TeacherPaymentRuleService service;



    // Create or update
    @PostMapping
    public TeacherPaymentRule saveRule(@RequestBody TeacherPaymentRule rule) {
        return service.saveRule(rule);
    }

    // Get all rules
    @GetMapping
    public List<TeacherPaymentRule> getAllRules() {
        return service.getAllRules();
    }

    // Get rule by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeacherPaymentRule> getRuleById(@PathVariable Integer id) {
        return service.getRuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete rule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Integer id) {
        service.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
