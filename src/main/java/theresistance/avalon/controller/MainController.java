package theresistance.avalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("")
    public String goLogIn(){
        return "redirect:/login.html";
    }

    @RequestMapping("/home")
    public String goHome(){

        return "home";
    }


}
