package kea.clbo.controller;


import kea.clbo.model.Member;
import kea.clbo.repository.IMemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {


    // Comment

    IMemberRepository repository;

    public MemberController(IMemberRepository repository){
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session){
        // if no session has been set earlier, then show the login page,
        // othervise if a ssession exists redirect to the secret page

        Object isl = session.getAttribute("isloggedin");
        if(isl != null && isl.equals("yes")){
            model.addAttribute("members", repository.readAll());
            return "secrets";
        }
        return "index";
    }
    @PostMapping("/")
    public String login(@ModelAttribute Member member, HttpSession session, Model model){
        Member memb = repository.read(member.getEmail());
        if(memb != null && member.getEmail().equals(memb.getEmail()) && member.getPassword().equals(memb.getPassword())){
            session.setAttribute("isloggedin","yes");
            model.addAttribute("members", repository.readAll());
            return "secrets";
        }
        return "denied";
    }

    @GetMapping("/create")
    public String create(){
        return "create";

    }

    @PostMapping("/create")
    public String create(@ModelAttribute Member member, HttpSession session, Model model){
        boolean isCreated = repository.create(member); // returns false if if member (email) already exists
                                                // returns true if a new member is created
        if (isCreated) {
            session.setAttribute("isloggedin", "yes");
        }
        return "index";
    }
}
