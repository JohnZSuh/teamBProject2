package com.project.reimbursement;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbRepository extends JpaRepository<Reimbursement, UUID> {

    // save to database
    String save(NewRequest newRequest);
}
