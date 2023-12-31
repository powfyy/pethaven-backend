package dev.pethaven.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table (name= "users")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String phoneNumber;

    @JoinColumn(name = "auth_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_pet",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> petSet = new HashSet<>();

    @OneToMany (mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public User(Long id, String name, String lastname, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
    }

    public User (String name, String lastname, String phoneNumber) {
        this(null, name, lastname, phoneNumber);
    }

}
