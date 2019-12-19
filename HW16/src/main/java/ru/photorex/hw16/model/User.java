package ru.photorex.hw16.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails{

    @Id
    private String id;

    @Field("userName")
    private String userName;

    @Field("password")
    private String password;

    @Field("fullName")
    private String fullName;

    @Field("isEnabled")
    private boolean isEnabled;

    @Field("isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Field("isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Field("isCredentialsNonExpired")
    private boolean isCredentialsNonExpired;

    @Field("roles")
    private Set<Role> roles;

    public User(String userName, String password, String fullName, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.isEnabled = true;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.roles = roles;
    }

    public enum Role implements GrantedAuthority {
        ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

        private final String authority;

        Role(String role) {
            this.authority = role;
        }

        public String getRole() {
            return authority;
        }

        @Override
        public String getAuthority() {
            return getRole();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return isEnabled;
    }
}
