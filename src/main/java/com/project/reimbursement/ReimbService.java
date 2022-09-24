package com.project.reimbursement;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.exceptions.InvalidRequestException;
import com.project.common.exceptions.ResourceNotFoundException;



@Service
public class ReimbService {

    private final ReimbRepository reimbRepo;

    @Autowired
    public ReimbService(ReimbRepository reimbRepo) {
        this.reimbRepo = reimbRepo;
    }

    public List<ReimbResponse> getAllReimbursements() {
        return reimbRepo.findAll()
                        .stream()
                        .map(ReimbResponse::new)
                        .collect(Collectors.toList());
    }

    public ReimbResponse getReimbById(String id) {
        try {
            return reimbRepo.findById(UUID.fromString(id))
                             .map(ReimbResponse::new)
                             .orElseThrow(ResourceNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("A valid uuid must be provided!");
        }
    }


    
}
