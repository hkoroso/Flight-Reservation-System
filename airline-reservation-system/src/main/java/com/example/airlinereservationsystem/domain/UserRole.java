package com.example.airlinereservationsystem.domain;

import com.example.airlinereservationsystem.util.constant.Roles;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
/**
 * @author Abdi Wako Jilo
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(value = EnumType.STRING)
    Roles roleName;

    @Override
    public String getAuthority() {
        return roleName.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRole userRole = (UserRole) o;
        return  Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
