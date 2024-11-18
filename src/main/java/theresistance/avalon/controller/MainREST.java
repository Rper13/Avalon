package theresistance.avalon.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theresistance.avalon.dto.GameDto;
import theresistance.avalon.model.GameEntity;
import theresistance.avalon.model.UserEntity;
import theresistance.avalon.repository.GameRepository;
import theresistance.avalon.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainREST {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameRepository gameRepository;


    @RequestMapping("/login_request")
    public Map<String,Object> logIn(@RequestParam String username, @RequestParam String password, HttpSession session){

        Map<String, Object> response = new HashMap<>();

        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);

        if(userEntity != null){

            if(password.equals(userEntity.getPassword())){

                response.put("success", true);
                response.put("message", "Login Successful.");
                session.setAttribute("user", userEntity);
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

        UserEntity userEntity = new UserEntity(username, password);

        userRepository.save(userEntity);

        session.setAttribute("user", userEntity);

        return response;
    }

    @GetMapping("/currentUser")
    public Map<String, Object> getCurrUser(HttpSession session){

        Map<String, Object> response = new HashMap<>();

        UserEntity userEntity = (UserEntity) session.getAttribute("user");

        if(userEntity != null){
            response.put("success", true);
            response.put("username", userEntity.getUsername());
            response.put("user_id", userEntity.getId());
        }else{
            response.put("success", false);
            response.put("username", "#NoUser");
        }
        return response;
    }

    @PostMapping("/addGame")
    public Map<String, Object> addGame(@RequestBody GameDto gameData, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        GameEntity gameEntity = new GameEntity();

        UserEntity creator = (UserEntity) session.getAttribute("user");

        System.err.println(creator.getId() + " " + creator.getUsername() + " " + creator.getPassword());

        gameEntity.setCreator_id(creator);
        gameEntity.setMax_players(gameData.getMax_players());
        gameEntity.setVoting_type(gameData.isVoting_type());
        gameEntity.setGameName(gameData.getGameName());

        gameRepository.save(gameEntity);

        response.put("success", true);

        return response;
    }



}
