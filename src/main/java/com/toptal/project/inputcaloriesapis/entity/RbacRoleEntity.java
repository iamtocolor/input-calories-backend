package com.toptal.project.inputcaloriesapis.entity;

import com.toptal.project.inputcaloriesapis.rbac.Roles;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rbac_role_table")
@Getter
public class RbacRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Roles role;

    @Column(name = "scope_id")
    private String scopeId;
}
