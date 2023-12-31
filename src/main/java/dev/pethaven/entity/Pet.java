package dev.pethaven.entity;

import dev.pethaven.enums.PetGender;
import dev.pethaven.enums.PetStatus;
import dev.pethaven.enums.PetType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PetGender gender;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PetType typePet;
    @Column(nullable = false)
    private LocalDate birthDay;
    @Column
    private String breed;
    @Column
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    PetStatus status;

    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    private User user;
    @Column(name="user_id")
    private Long userId;

    @JoinColumn(name = "organization_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
    private Organization organization;
    @Column(name="organization_id")
    private Long organizationId;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<PetPhotos> petPhotos;

    @ManyToMany(mappedBy = "petSet")
    private Set<User> userSet = new HashSet<>();


    public Pet(Long id,
               String name,
               PetGender gender,
               PetType typePet,
               LocalDate birthDay,
               String breed,
               String description,
               PetStatus status) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.typePet = typePet;
        this.birthDay = birthDay;
        if (breed != null) {
            this.breed = breed.toLowerCase();
        } else {
            this.breed = null;
        }
        this.description = description;
        this.status = status;
    }

    public Pet(String name,
               PetGender gender,
               PetType typePet,
               LocalDate birthDay,
               String breed,
               String description,
               PetStatus status) {
        if (breed != null) {
            this.breed = breed.toLowerCase();
        } else {
            this.breed = null;
        }
        this.id = null;
        this.name = name;
        this.gender = gender;
        this.typePet = typePet;
        this.birthDay = birthDay;
        this.description = description;
        this.status = status;
    }

    public void setOrganization(Organization organization) {
        setOrganizationId(organization.getId());
        this.organization = organization;
    }

    public void setUser(User user) {
        setUserId(user.getId());
        this.user = user;
    }
}
