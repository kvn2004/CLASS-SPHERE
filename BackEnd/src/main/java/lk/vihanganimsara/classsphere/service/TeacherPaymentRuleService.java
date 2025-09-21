package lk.vihanganimsara.classsphere.service;

import lk.vihanganimsara.classsphere.entity.TeacherPaymentRule;
import lk.vihanganimsara.classsphere.repository.TeacherPaymentRuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TeacherPaymentRuleService {

    private final TeacherPaymentRuleRepository repository;

    public TeacherPaymentRuleService(TeacherPaymentRuleRepository repository) {
        this.repository = repository;
    }

    // Create or update
    public TeacherPaymentRule saveRule(TeacherPaymentRule rule) {
        return repository.save(rule);
    }

    // Get all rules
    public List<TeacherPaymentRule> getAllRules() {
        return repository.findAll();
    }

    // Get a rule by ID
    public Optional<TeacherPaymentRule> getRuleById(Integer id) {
        return repository.findById(id);
    }

    // Delete a rule by ID
    public void deleteRule(Integer id) {
        repository.deleteById(id);
    }
}
