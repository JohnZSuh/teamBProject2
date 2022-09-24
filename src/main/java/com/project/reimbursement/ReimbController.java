package com.project.reimbursement;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.common.SecurityUtils.*;

@RestController
@RequestMapping("/reimb")
public class ReimbController {

    private static Logger logger = LogManager.getLogger(ReimbController.class);
        
    private final ReimbService reimbService;

    @Autowired
    public ReimbController(ReimbService reimbService) {
        this.reimbService = reimbService;
    }

    @GetMapping(produces = "application/json")
    public List<ReimbResponse> getAllReimbursements(HttpServletRequest req) {
        logger.info("A GET request was received by /reimb at {}", LocalDateTime.now());
        HttpSession userSession = req.getSession(false);
        enforceAuthentication(userSession);
        enforcePermissions(userSession, "FINANCE_MANAGER");
        return reimbService.getAllReimbursements();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ReimbResponse getReimbById(@PathVariable String id, HttpSession userSession) {
        logger.info("A GET request was received by /reimb/{id} at {}", LocalDateTime.now());
        enforceAuthentication(userSession);
        enforcePermissions(userSession, "FINANCE_MANAGER");
        return reimbService.getReimbById(id);
    }
    
}
