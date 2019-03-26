package club.majoy.springbootmajoycache.controller;

import club.majoy.springbootmajoycache.entity.User;
import club.majoy.springbootmajoycache.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")//
public class UserController {
    @Resource
    private UserService userService;

    @PutMapping
    public User add(@RequestBody User user){
        User u = userService.save(user);
        return u;
    }
    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Integer id) {

        return userService.delUser(id);

    }

    @GetMapping("{id}")
    public User getUser(@PathVariable Integer id){
        return userService.findUser(id);
    }



    @PostMapping
    public User update(@RequestBody User user){
        return  userService.save(user);
    }

}
