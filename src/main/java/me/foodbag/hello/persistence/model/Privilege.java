package me.foodbag.hello.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "priviledge")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long privilegeId;

    private String name;

    @ManyToMany(mappedBy = "privilege")
    private Collection<Role> roles;



}
