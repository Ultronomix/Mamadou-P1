package com.revature.ers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.auth.AuthService;
import com.revature.ers.auth.AuthServlet;
import com.revature.ers.users.UserDAO;
import com.revature.ers.users.UserService;
import com.revature.ers.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import com.revature.ers.reimbursements.ReimbursementServlet;
import com.revature.ers.reimbursements.ReimbursementService;
import com.revature.ers.reimbursements.ReimbursementDAO;
public class ErsAppMain {
    public static void main(String[] args) throws LifecycleException {
        String docBase = System.getProperty("java.io.tmpdir");

        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(docBase);
        webServer.setPort(8080);
        webServer.getConnector();


        UserDAO userDAO = new UserDAO();
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
        ReimbursementService reimbService = new ReimbursementService(reimbursementDAO);
        ReimbursementServlet reimbServlet = new ReimbursementServlet(reimbService);
        AuthService authService = new AuthService(userDAO);
        UserService userService = new UserService(userDAO);
        ObjectMapper jsonMapper = null;
        UserServlet userServlet = new UserServlet(userService, jsonMapper);
        AuthServlet authServlet = new AuthServlet(authService, jsonMapper);
        jsonMapper = new ObjectMapper();


        // Web server context and servlet configurations
        final String rootContext = "/taskmaster";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", authServlet).addMapping("/auth");
        webServer.addServlet(rootContext, "ReimbServlet", reimbServlet).addMapping("/reimb");
        webServer.start();
        webServer.getServer().await();
    }
}







