package org.yasmani.io.apprestwithtoken.controller;

import org.springframework.web.bind.annotation.*;
import org.yasmani.io.apprestwithtoken.model.MemberShip;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final Map<Long, MemberShip> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @GetMapping
    public Collection<MemberShip> getUsers() {
        return users.values();
    }

    @GetMapping("{id}")
    public MemberShip getUSer(@PathVariable Long id) {
        var user = users.get(id);
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        return user;
    }

    @PostMapping
    public MemberShip createUser(@RequestBody MemberShip memberShip) {
        var id = counter.getAndIncrement();
        memberShip = new MemberShip(id, memberShip.username(), memberShip.password(),memberShip.rol());
        users.put(id, memberShip);
        return memberShip;
    }

    @PutMapping("{id}")
    public MemberShip updateUser(@PathVariable Long id, @RequestBody MemberShip memberShip) {
        var existingUser = users.get(id);
        if (existingUser == null) {
            throw new NoSuchElementException("User not found");
        }
        memberShip = new MemberShip(id, memberShip.username(), memberShip.password(),memberShip.rol());
        users.put(id, memberShip);
        return memberShip;
    }
}
