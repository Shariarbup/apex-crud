package com.example.apexcrud.model;

import com.example.apexcrud.enums.ActiveStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=1, max = 50, message = "UserId must be min of 1 chars and max of 50 characters")
    @Column(name = "user_id")
    private String userId;

    @NotEmpty
    @Size(min=1, max = 255, message = "UserName must be min of 1 chars and max of 255 characters")
    @Column(name = "user_name")
    private String userName;

    @Size(max = 255, message = "Designation must be min of 1 chars and max of 255 characters")
    @Column(name = "designation")
    private String designation;

    @Size(max = 255, message = "DeptmsCode must be min of 1 chars and max of 255 characters")
    @Column(name = "deptms_code")
    private String deptmsCode;

    @Size(max = 255, message = "Email must be min of 1 chars and max of 255 characters")
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Size(min=1, max = 255, message = "Password must be min of 1 chars and max of 50 characters")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActiveStatus status = ActiveStatus.ACTIVE;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate lastModifiedDate;

    @CreatedBy
    @Column(
            nullable = true,
            updatable = false
    )
    private Long createdBy;

    @LastModifiedBy
    @Column(
            insertable = false
    )
    private Long lastModifiedBy;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}