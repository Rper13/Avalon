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
import java.util.List;
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
                session.removeAttribute("user");
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

        session.removeAttribute("user");
        session.setAttribute("user", userEntity);

        response.put("success",true);

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

    @GetMapping("/userById")
    public Map<String, Object> userById(@RequestParam Long user_id){
        Map<String, Object> response = new HashMap<>();

        String username = userRepository.getReferenceById(user_id).getUsername();

        response.put("success",true);
        response.put("username", username);

        return response;
    }

    @PostMapping("/addGame")
    public Map<String, Object> addGame(@RequestBody GameDto gameData, HttpSession session){
        Map<String, Object> response = new HashMap<>();

        GameEntity gameEntity = new GameEntity();

        UserEntity creator = (UserEntity) session.getAttribute("user");

        gameEntity.setCreator_id(creator);
        gameEntity.setMax_players(gameData.getMax_players());
        gameEntity.setVoting_type(gameData.isVoting_type());
        gameEntity.setGameName(gameData.getGameName());
        gameEntity.setStatus(1);

        gameRepository.save(gameEntity);

        response.put("success", true);

        return response;
    }

    @PostMapping("/loadGames")
    public Map<String, Object> loadGames(){
        Map<String, Object> response = new HashMap<>();

        List<GameEntity> gameEntities = gameRepository.getGames();

        List<GameDto> games = gameEntities.stream().map( game -> new GameDto(
                game.getGame_id(),
                game.getGameName(),
                game.isVoting_type(),
                game.getMax_players(),
                game.getStatus(),
                game.getCreator_id().getId()
        )).toList();

        response.put("success", true);
        response.put("games", games);

        return response;
    }


}
