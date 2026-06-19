package com.library.service;

import com.library.entity.Fine;
import java.util.List;

public interface FineService {

    Fine calculateFine(Long issueId);

    List<Fine> getAllFines();

    Fine markFineAsPaid(Long fineId);
}