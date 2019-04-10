package com.realpacific.springclouddemo.controllers;

import com.realpacific.springclouddemo.entity.User;
import com.realpacific.springclouddemo.exceptions.UserNotFoundException;
import com.realpacific.springclouddemo.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
public class UserController {
    @Autowired
    private UserDaoService service;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }


    @GetMapping("/users/{id}")
    public Resource<User> retrieveOneUsers(@PathVariable("id") Integer id) {
        User user = service.findOne(id);
        if (user == null)
            throw new UserNotFoundException("id: " + id);

        // Return the links to all users while retrieving single user ...
        // ... in such a way that even when /users endpoint is changed the HATEOAS is updated as well
        // hateoas's Resource
        Resource<User> resource = new Resource<>(user);
        // Allows us to create links from the methods in the @Controller
        // we are getting the url mapped to UserController#retrieveAllUsers()
        ControllerLinkBuilder linkTo =
                ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }


    @PostMapping("/users")
    public ResponseEntity createNewUser(@Valid @RequestBody User user) {
        user.setBirthDate(new Date());
        User savedUser = service.save(user);

        // Append id to the current uri i.e /user/{id}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        // Will return 201 Created HTTP Status with location header as users/id
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        User user = service.deleteById(id);
        if (user == null)
            throw new UserNotFoundException("User with " + id + " was not found.");
    }


    @GetMapping("/greet")
    public String sayHello() {
        // Auto pick up the Accept-Header
        return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
    }
}
