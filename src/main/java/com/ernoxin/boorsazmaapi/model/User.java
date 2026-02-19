package com.ernoxin.boorsazmaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @NotBlank
    @Column(nullable = false)
    private String firstName;

    @NotBlank
    @Column(nullable = false)
    private String lastName;

    @Pattern(regexp = "^\\d{10}$")
    @Column(nullable = false, unique = true)
    private String nationalCode;

    @Pattern(regexp = "^\\+98\\d{10}$")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;
}
