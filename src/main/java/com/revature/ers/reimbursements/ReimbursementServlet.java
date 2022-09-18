package com.revature.ers.reimbursements;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ers.common.ErrorResponse;
import com.revature.ers.common.exceptions.DataSourceException;
import com.revature.ers.common.exceptions.InvalidRequestException;
import com.revature.ers.common.exceptions.ResourceNotFoundException;
import com.revature.ers.users.UserResponse;

public class ReimbursementServlet extends HttpServlet{

    private final ReimbursementService reimbursementServ;

    public ReimbursementServlet(ReimbursementService reimbursementServ){
        this.reimbursementServ = reimbursementServ;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");


        HttpSession userSession = req.getSession(false);

        if(userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "ERROR 401: Authorization Missing")));
            return;
        }

        String usernameSubmission = req.getParameter("username");
        String searchStatus = req.getParameter("status");
        String searchIDField = req.getParameter("searchID");
        int searchID = 0;

        if(usernameSubmission == null){
            usernameSubmission = "";
        }
        if(searchStatus == null){
            searchStatus = "";
        }
        if(searchIDField != null){
            searchID = Integer.parseInt(searchIDField);
        }

        UserResponse requester = (UserResponse) userSession.getAttribute("authUser");

        if(searchID <=0){

            if(requester.getRole().equals("employee") && (!requester.getUsername().equals(usernameSubmission))){
                resp.setStatus(403);
                resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "ERROR 403: Authorization Insufficent to Access")));
                return;
            }
        }

        try{
            if(searchID > 0){
                Reimbursements specificReimbursement = reimbursementServ.getReimbs(searchID);

                if(!requester.getRole().equals("employee") || requester.getUserID() == specificReimbursement.getCreatorID()){
                    resp.getWriter().write(jsonMapper.writeValueAsString(specificReimbursement));
                }else{
                    resp.getWriter().write(jsonMapper.writeValueAsString("You do not have access to the details of the searched Reimbursement"));
                }

            }else

                if(requester.getRole().equals("employee") && requester.getUsername().equals(usernameSubmission) && searchStatus.equals("pending")){
                    List<Reimbursements> ownedReimb = reimbursementServ.pendingReimb(usernameSubmission);
                    resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
                }else

                    if(requester.getRole().equals("employee") && requester.getUsername().equals(usernameSubmission)){
                        List<Reimbursements> ownedReimb = reimbursementServ.getOwnedReimbs(usernameSubmission);
                        resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
                    }else

                        if(!requester.getRole().equals("employee") && searchStatus.equals("pending")){
                            List<Reimbursements> ownedReimb = reimbursementServ.getAllPendingReimbs(usernameSubmission);
                            resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
                        }else

                            if(!requester.getRole().equals("employee")){
                                List<Reimbursements> ownedReimb = reimbursementServ.getAllReimbs(usernameSubmission);
                                resp.getWriter().write(jsonMapper.writeValueAsString(ownedReimb));
                            }else{
                                throw new InvalidRequestException("Request format does not match any doGet method");
                            }
        }catch(InvalidRequestException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        }catch(ResourceNotFoundException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
        }catch(DataSourceException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession userSession = req.getSession(false);


        if(userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "ERROR 401: Authorization Missing")));
            return;
        }

        UserResponse requester = (UserResponse) userSession.getAttribute("authUser");


        try{
            NewReimbursementRequest requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            requestBody.setAuthorID(requester.getUserID());
            Reimbursements responseBody = reimbursementServ.generate(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

        } catch (InvalidRequestException | JsonMappingException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

        } catch (DataSourceException e) {
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));

        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");


        HttpSession userSession = req.getSession(false);

        if(userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "ERROR 401: Authorization Missing")));
            return;
        }
        UserResponse requester = (UserResponse) userSession.getAttribute("authUser");

        if(requester.getRole().equals("employee")){
            String response = "Employees may not approve or deny reimbursements. Contact an Admin or Finance Manager";
            resp.getWriter().write(jsonMapper.writeValueAsString(response));
            return;
        }

        try{
            ReimbursementStatus requestBody = jsonMapper.readValue(req.getInputStream(), ReimbursementStatus.class);
            Reimbursements responseBody = reimbursementServ.updateReimb(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

        }catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

        } catch (DataSourceException e) {
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));

        }
    }

}
