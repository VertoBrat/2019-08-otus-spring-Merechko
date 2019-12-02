package ru.photorex.hw14.model.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

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

    public enum Role {
        ROLE_ADMIN("ROLE_ADMIN"), ROLE_USER("ROLE_USER");

        private final String authority;

        Role(String role) {
            this.authority = role;
        }

        public String getRole() {
            return authority;
        }
    }
}
