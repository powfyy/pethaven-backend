package dev.pethaven.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nameOrganization;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String passportSeries;
    @Column(nullable = false)
    private String passportNumber;
    @Column(nullable = false)
    private String phoneNumber;

    @JoinColumn(name = "auth_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private Set<Chat> chats = new HashSet<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    public Organization(Long id,
                        String nameOrganization,
                        String city,
                        String passportSeries,
                        String passportNumber,
                        String phoneNumber) {
        this.id = id;
        this.nameOrganization = nameOrganization;
        this.city = city;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.phoneNumber = phoneNumber;
    }

    public Organization(String nameOrganization,
                        String city,
                        String passportSeries,
                        String passportNumber,
                        String phoneNumber) {
        this(null, nameOrganization, city, passportSeries, passportNumber, phoneNumber);
    }
}
