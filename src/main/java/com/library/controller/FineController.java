package com.library.controller;

import com.library.service.FineService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @GetMapping
    public String getAllFines(Model model) {
        model.addAttribute("fines", fineService.getAllFines());
        return "fines/fine-list";
    }

    @GetMapping("/calculate/{issueId}")
    public String calculateFine(@PathVariable Long issueId) {
        fineService.calculateFine(issueId);
        return "redirect:/fines";
    }

    @GetMapping("/paid/{fineId}")
    public String markFineAsPaid(@PathVariable Long fineId) {
        fineService.markFineAsPaid(fineId);
        return "redirect:/fines";
    }
}