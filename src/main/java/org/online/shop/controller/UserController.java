package org.online.shop.controller;

import org.online.shop.entity.AuthorityEntity;
import org.online.shop.entity.UserEntity;
import org.online.shop.repository.AuthorityRepository;
import org.online.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/web")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user/list")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView("user/users");
        List<UserEntity> userEntityList = userRepository.findAll();
        modelAndView.addObject("userList", userRepository.findAll());
        for (UserEntity userEntity:userEntityList){
            modelAndView.addObject("userAuthorities",userEntity.getAuthorityList());
        }
        return modelAndView;
    }

    @GetMapping("/user/add")
    public ModelAndView addUser() {
        ModelAndView modelAndView = new ModelAndView("/user/user-form");
        modelAndView.addObject("userObject", new UserEntity());
        return modelAndView;
    }

    @PostMapping("/user/save")
    public ModelAndView saveUser(@ModelAttribute("userObject") UserEntity userEntity) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/form-login");
        userEntity.setEnabled(true);
        Boolean isNewUser = userEntity.getUserId() == null ? true : false;

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (!isNewUser) {
            if (userEntity.getPassword() != null && !userEntity.getPassword().trim().isEmpty()) {
                userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            } else {
                UserEntity oldUser = userRepository.getOne(userEntity.getUserId());
                userEntity.setPassword(oldUser.getPassword());
            }
        }
        userEntity = userRepository.save(userEntity);
        if (isNewUser) {
            AuthorityEntity authority = new AuthorityEntity();
            authority.setUsername(userEntity.getUsername());
            authority.setAuthority("USER");
            authority.setUser(userEntity);
            authorityRepository.save(authority);
        }


        return modelAndView;
    }

    @GetMapping("/user/edit")
    public ModelAndView editUser(){
        ModelAndView modelAndView = new ModelAndView("user/user-form");
        Optional<User> user = loggedInUser();
        if (user.isPresent()){
            modelAndView.addObject("userObject",userRepository.findByUsername(user.get().getUsername()));
        }
        return modelAndView;
    }

    public Optional<User> loggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal()instanceof User){
            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }

}
