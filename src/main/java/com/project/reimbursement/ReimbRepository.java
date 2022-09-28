package com.project.reimbursement;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReimbRepository extends JpaRepository<Reimbursement, UUID> {

    // save to database
    void save(NewRequest newRequest);

    @Query(nativeQuery = true, value = "UPDATE ers_reimbursements SET resolver_id = :aId, resolved = current_date, status_id = :sId WHERE reimb_id = :rId")
    void updateStatus(@Param("aId") String resolverId, @Param("sId") String statusId, @Param("rId") String reimbId);
}
