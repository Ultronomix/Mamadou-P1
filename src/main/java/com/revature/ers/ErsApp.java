package com.revature.ers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.auth.AuthService;
import com.revature.ers.auth.AuthServlet;
import com.revature.ers.users.UserDAO;
import com.revature.ers.users.UserService;
import com.revature.ers.users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErsApp {

    private static Logger logger = LogManager.getLogger(ErsApp.class);

    public static void main(String[] args) throws LifecycleException {

        logger.info("Starting ERS application");

        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();

        // Web server base configurations
        webServer.setBaseDir(docBase);
        webServer.setPort(5000); // defaults to 8080, but we can set it to whatever port we want (as long as its open)
        webServer.getConnector(); // formality, required in order for the server to receive requests

        // App component instantiation
        UserDAO userDAO = new UserDAO();
        AuthService authService = new AuthService(userDAO);
        UserService userService = new UserService(userDAO);
        ObjectMapper jsonMapper = new ObjectMapper();
        UserServlet userServlet = new UserServlet(userService, jsonMapper);
        AuthServlet authServlet = new AuthServlet(authService, jsonMapper);

        // Web server context and servlet configurations
        final String rootContext = "/ers";
        webServer.addContext(rootContext, docBase);
        webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
        webServer.addServlet(rootContext, "AuthServlet", authServlet).addMapping("/auth");

        // Starting and awaiting web requests
        webServer.start();
        logger.info("ERS web application successfully started");
        webServer.getServer().await();

    }

}