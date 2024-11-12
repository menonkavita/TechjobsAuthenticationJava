package org.launchcode.techjobsauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.launchcode.techjobsauth.models.User;
import org.launchcode.techjobsauth.models.data.AuthenticationController;
import org.launchcode.techjobsauth.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/css");

    private static boolean isWhitelisted(String path){
        for(String pathRoot : whitelist){
            if(path.startsWith(pathRoot)){
                return true;
            }
        }
        return false;
    }

    // Signature of our method must match the definition of preHandle and HandlerInterceptor
    // exactly, even if we don’t need all of the parameters.
    // Checking if the user is logged in i.e. session is not timed out
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException{

        // No sign-in required to view white-listed pages
        if(isWhitelisted(request.getRequestURI())){
            return true;
        }


        // preHandle and the overridden method in HandlerInterceptor throw different exception types.
        // This is the one way in which the method signatures are allowed to differ, so long as the
        // exception type of our method is a subclass of the overridden method.
        // Since IOException extends Exception, this is allowed.
        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        // User is logged in
        if(user != null){
            return true;
        }

        // User is not logged in
        response.sendRedirect("/login");
        return false;
    }
}
