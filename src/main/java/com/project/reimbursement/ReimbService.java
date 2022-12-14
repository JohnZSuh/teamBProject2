package com.project.reimbursement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.common.ResourceCreationResponse;
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

    public ResourceCreationResponse createNewRequest(NewRequest newRequest, UUID authorId) {

        Reimbursement insertRequest = newRequest.extractEntity();

        // Set the author id from the login user
        insertRequest.setAuthorId(authorId);

        // Set the submitted time to now
        insertRequest.setSubmitted(LocalDateTime.now());
        
        // Defaults the status to pending
        Status status = new Status();
        status.setStatusId(UUID.fromString("UUID for pending"));
        status.setStatus("Pending");
        insertRequest.setStatusId(status);

        // Add the type. Need if statement for each type.
        if (newRequest.getType().toUpperCase().equals("OTHER")){
            
            Type type = new Type();
            type.setTypeId(UUID.fromString("UUID for type Other"));
            type.setType("Other");
            insertRequest.setTypeId(type);
        }

        if (newRequest.getType().toUpperCase().equals("TRAVEL")){
            
            Type type = new Type();
            type.setTypeId(UUID.fromString("UUID for type Travel"));
            type.setType("Travel");
            insertRequest.setTypeId(type);
        }

        reimbRepo.save(insertRequest);

        // Return uuid for reimbursement request
        return new ResourceCreationResponse("ReimbID: " + insertRequest.getId());
    }


    
}
