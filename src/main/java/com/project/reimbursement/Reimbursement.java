package com.project.reimbursement;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ers_reimbursements")
public class Reimbursement {

    @Id
    @Column(name = "reimb_id", nullable = false)
    private UUID id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String submitted;

    @Column
    private String resolved;

    @Column(nullable = false)
    private String description;
    
    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "resolver_id")
    private UUID resolverId;
    
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private UUID statusId;
    
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private UUID typeId;
    
    public Reimbursement() {
        super();
    }

    public Reimbursement(UUID id, double amount, String submitted, String resolved,
             String description, UUID authorId, UUID resolverId, UUID statusId, UUID typeId) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.resolved = resolved;
        this.description = description;
        this.authorId = authorId;
        this.resolverId = resolverId;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getResolverId() {
        return resolverId;
    }

    public void setResolverId(UUID resolverId) {
        this.resolverId = resolverId;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getTypeId() {
        return typeId;
    }

    public void setTypeId(UUID typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement reimbursement = (Reimbursement) o;
        return Objects.equals(id, reimbursement.id) && Objects.equals(amount, reimbursement.amount)
            && Objects.equals(submitted, reimbursement.submitted) && Objects.equals(resolved, reimbursement.resolved) 
            && Objects.equals(description, reimbursement.description) && Objects.equals(authorId, reimbursement.authorId) 
            && Objects.equals(resolverId, reimbursement.resolverId) && Objects.equals(statusId, reimbursement.statusId)
            && Objects.equals(typeId, reimbursement.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitted, resolved, description, authorId, resolverId, statusId, typeId);
    }

    @Override
    public String toString() {
        return "Reimbursement [ " +
               "amount = '" + amount + "', " +
               "authorId = '" + authorId + "', " +
               "description = '" + description + "', " +
               "id = '" + id + "', " + 
               "resolved = '" + resolved + "', " +
               "resolverId = '" + resolverId + "', " +
               "statusId = '" + statusId + "', " +
                "submitted = '" + submitted + ", " + 
                "typeId = '" + typeId + "' " +
                "]";
    }

    
}
