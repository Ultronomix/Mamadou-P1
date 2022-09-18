package com.revature.ers;


import com.revature.ers.auth.*;
import com.revature.ers.reimbursements.ReimbursementDAO;
import com.revature.ers.reimbursements.ReimbursementService;
import com.revature.ers.reimbursements.ReimbursementServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import com.revature.ers.users.UserDAO;
import com.revature.ers.users.UserService;
import com.revature.ers.users.UserServlet;


public class ErsAppMain {
    public static void main(String[] args) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");

        //Creating webServer
        Tomcat webServer = new Tomcat();
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); //default is 8080
        webServer.getConnector(); //formality, connects server requests to application

        //Creating Data Access Objects (DAOs)
        UserDAO userDAO = new UserDAO();
        ReimbursementDAO reimbursementDAO = new ReimbursementDAO();

        //Service methods
        UserService userServ = new UserService(userDAO);
        AuthService authServ = new AuthService(userDAO);
        ReimbursementService reimbServ = new ReimbursementService(reimbursementDAO);

        //Connecting Servlets to Service layer
        UserServlet userSlet = new UserServlet(userServ);
        AuthServlet authSlet = new AuthServlet(authServ);
        ReimbursementServlet reimbSlet = new ReimbursementServlet(reimbServ);

        //Connecting Servlets to webServer
        final String rootContext = "/p1";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userSlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", authSlet).addMapping("/auth");
        webServer.addServlet(rootContext, "ReimbursementServlet", reimbSlet).addMapping("/reimbursements");


        webServer.start();
        webServer.getServer().await();
        System.out.println("ERS APP has started!");
    }
}
