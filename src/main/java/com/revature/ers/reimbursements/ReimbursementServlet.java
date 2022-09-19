package com.revature.ers.reimbursements;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.users.UserResponse;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.revature.ers.common.ErrorResponse;
import com.revature.ers.common.exceptions.InvalidRequestException;
import com.revature.ers.common.exceptions.DataSourceException;
import com.revature.ers.common.exceptions.ResourceNotFoundException;
import com.revature.ers.common.ResourceCreationResponse;
import com.revature.ers.common.exceptions.AuthenticationException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReimbursementServlet extends HttpServlet {

    private final ReimbursementService reimbService;

    public ReimbursementServlet(ReimbursementService reimbService) {
        this.reimbService = reimbService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        HttpSession reimbSession = req.getSession(false);
        if (reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requestor not authenticated with server, log in")));
            return;
        }
        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");
        String idToSearchFor = req.getParameter("id");
        String statusToSearchFor = req.getParameter("status");
        String typeToSearchFor = req.getParameter("type");
        String sort = req.getParameter("sort");

        // Manager can see all employee can only see theirs
        if (requester.getRole().equals("ADMIN") || (!requester.getRole().equals("MANAGER") && (requester.getRole().equals("EMPLOYEE") && !requester.getUser_Id().equals(idToSearchFor)))) {
            resp.setStatus(403); // Forbidden
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }

        try {
            List<Reimbursements> allReimb = reimbService.getAllReimb();
            if (idToSearchFor != null) {
                if (requester.getRole().equals("MANAGER")) {
                    allReimb.removeIf(reimb -> reimb.getResolverId() == null || !reimb.getResolverId().equals(idToSearchFor));
                } else {
                    allReimb.removeIf(reimb -> !reimb.getAuthorId().equals(idToSearchFor));
                }
            }
            if (statusToSearchFor != null) {
                allReimb.removeIf(reimb -> !reimb.getStatus().getName().equals(statusToSearchFor));
            }
            if (typeToSearchFor != null) {
                allReimb.removeIf(reimb -> !reimb.getType().getName().equals(typeToSearchFor));
            }
            if (sort != null && sort.equals("true")) {
                allReimb.sort(Comparator.comparing(Reimbursements::getSubmitted));
            }
            resp.getWriter().write(jsonMapper.writeValueAsString(allReimb));
        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (ResourceNotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
        } catch (DataSourceException e) {
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        HttpSession reimbSession = req.getSession(false);
        if (reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requestor not authenticated with server, log in")));
            return;
        }
        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");
        // ONLY EMPLOYEES CAN CREATE REIMB REQ
        if (!requester.getRole().equals("EMPLOYEE")) {
            resp.setStatus(403); // Forbidden
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }
        try {
            NewReimbursementRequest reimb = jsonMapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            com.revature.ers.common.ResourceCreationResponse responseBody = reimbService
                    .createReimb(reimb);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);// * bad request
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (AuthenticationException e) {
            resp.setStatus(409); // * conflit; indicates provided resource could not be saved
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
        } catch (DataSourceException e) {
            resp.setStatus(500); // * internal error
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        HttpSession reimbSession = req.getSession(false);
        if (reimbSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper
                    .writeValueAsString(new ErrorResponse(401, "Requestor not authenticated with server, log in")));
            return;
        }

        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");
        String idToSearchFor = req.getParameter("id");
        String status = req.getParameter("status");
        Reimbursements reimb = reimbService.getReimbById(idToSearchFor);
        // Manager can see all employee can only see theirs
        if (requester.getRole().equals("ADMIN") || (!requester.getRole().equals("MANAGER") &&
                (requester.getRole().equals("EMPLOYEE") && !reimb.getAuthorId().equals(requester.getUser_Id()) && !reimb.getStatus().getName().equals("PENDING")))) {
            resp.setStatus(403); // Forbidden
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
            return;
        }
        if (requester.getRole().equals("MANAGER")) {
            ResourceCreationResponse responseBody = reimbService.approveDeny(reimb.getReimbId(), requester.getUser_Id(), status);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
            return;
        }

    }
}

