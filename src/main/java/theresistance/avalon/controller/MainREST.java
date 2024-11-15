package theresistance.avalon.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import theresistance.avalon.model.User;
import theresistance.avalon.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainREST {

    @Autowired
    UserRepository userRepository;


    @RequestMapping("/login_request")
    public Map<String,Object> logIn(@RequestParam String username, @RequestParam String password, HttpSession session){

        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByUsername(username).orElse(null);

        if(user != null){

            if(password.equals(user.getPassword())){

                response.put("success", true);
                response.put("message", "Login Successful.");
                session.setAttribute("user", user);
                return response;
            }

            response.put("success", false);
            response.put("message", "Wrong Password.");
            return response;
        }

        response.put("success", false);
        response.put("message", "Username not found.");
        return response;
    }

    @GetMapping("/register_request")
    public Map<String, Object> register(@RequestParam String username, @RequestParam String password, HttpSession session){

        Map<String, Object> response = new HashMap<>();

        if(userRepository.userExists(username)){
            response.put("success", false);
            response.put("message", "Username is already taken.");
            return response;
        }

        User user = new User(username, password);

        userRepository.save(user);

        session.setAttribute("user", user);

        return response;
    }



}
