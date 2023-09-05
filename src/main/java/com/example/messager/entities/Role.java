package com.example.messager.entities;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<com.example.messager.entities.User> users;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<com.example.messager.entities.User> getUsers() {
        return users;
    }

    public void setUsers(Set<com.example.messager.entities.User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}
