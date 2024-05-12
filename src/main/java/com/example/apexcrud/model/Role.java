package com.example.apexcrud.model;

import com.example.apexcrud.enums.RoleType;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    @Id
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
