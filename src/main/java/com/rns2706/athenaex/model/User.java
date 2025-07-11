package com.rns2706.athenaex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rns2706.athenaex.domain.UserRole;
import com.rns2706.athenaex.domain.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String mobile;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserStatus status= UserStatus.PENDING;

    @Column(nullable = true)
    private boolean isVerified = false;

    @Embedded
    private TwoFactAuth twoFactAuth = new TwoFactAuth();

    private String picture;
    private UserRole role = UserRole.ROLE_USER;

}








