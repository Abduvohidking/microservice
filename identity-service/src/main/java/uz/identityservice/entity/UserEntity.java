package uz.identityservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.identityservice.enums.Permission;

import java.security.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", updatable = false,nullable = false)
    private Date CreatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date UpdatedAt;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String mobilePhone;

    private String fullName;

    @Column(unique = true)
    private String pinfl;

    private String birthDate;

    public UserEntity(String username, String password, String mobilePhone) {
        this.username = username;
        this.password = password;
        this.mobilePhone = mobilePhone;
    }

    private boolean enabled;

    private boolean isRegistered;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Permission[] values = Permission.values();
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Permission value : values) {
            grantedAuthorities.add(new SimpleGrantedAuthority(value.name()));
        }
        return grantedAuthorities;
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
        return this.enabled;
    }


}
