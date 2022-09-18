package com.revature.ers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.ers.users.UserResponse;
import com.revature.ers.common.ErrorResponse;
import com.revature.ers.common.exceptions.AuthenticationException;
import com.revature.ers.common.exceptions.DataSourceException;
import com.revature.ers.common.exceptions.InvalidRequestException;

public class AuthServlet extends HttpServlet{

    private final AuthService authenticationServ;

    public AuthServlet(AuthService authenticationServ){
        this.authenticationServ = authenticationServ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //TODO add logging based on 9/9 lecture

        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        try{
            Credentials userCredentials = jsonMapper.readValue(req.getInputStream(), Credentials.class);
            UserResponse response = authenticationServ.authenticate(userCredentials);
            resp.setStatus(200);

            //TODO add logging here marking successful authentication per 9/9 lecture


            HttpSession userSession = req.getSession();
            userSession.setAttribute("authUser", response);

            resp.getWriter().write(jsonMapper.writeValueAsString(response));

            //TODO add logging here marking successful HTTP Header assignment per 9/9 lecture

        }catch(InvalidRequestException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(400);//BAD REQUEST
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        }catch(AuthenticationException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(401);//UNAUTHORIZED
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, e.getMessage())));
        }catch(DataSourceException e){
            //TODO add logging based on 9/9 lecture
            resp.setStatus(500);//SERVER ERROR
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        req.getSession().invalidate();
        resp.setStatus(200);
        resp.getWriter().write(jsonMapper.writeValueAsString("Logged out"));
    }
}
