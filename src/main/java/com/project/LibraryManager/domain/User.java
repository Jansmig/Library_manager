package com.project.LibraryManager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @NotNull
    @Column
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    private LocalDateTime userCreationDate;

    @OneToMany(
            fetch = FetchType.LAZY,
            targetEntity = Rental.class,
            mappedBy = "user"
    )
    private List<Rental> rentals = new ArrayList<>();

    public User() {
        this.userCreationDate = LocalDateTime.now();
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userCreationDate = LocalDateTime.now();
    }

    public void addRental(Rental rental){
        this.rentals.add(rental);
    }

    @Override
    public String toString() {
        return  "userId = " + id + "\n" +
                "firstName = " + firstName + "\n" +
                "lastName = " + lastName + "\n" +
                "userCreationDate = " + userCreationDate;
    }
}
