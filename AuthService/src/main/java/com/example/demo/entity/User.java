package com.example.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",schema = "noteappschema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userid")
    private long userId;

    @Column(nullable = false,name = "username")
    private String username;

    @Column(nullable = false,name = "password")
    private String password;

    @Column(nullable = false,name = "createdate")
    @CreationTimestamp
    private OffsetDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updatedate")
    private OffsetDateTime updateDate;

    @Column(nullable = false,name = "isenabled")
    private boolean isEnabled;

    @Column(nullable = false,name = "isaccountnonlocked")
    private boolean isAccountNonLocked;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            schema = "noteappschema",
            joinColumns = @JoinColumn(name = "userid"),            // FK to users
            inverseJoinColumns = @JoinColumn(name = "roleid")      // FK to roles
    )
    private Set<Role> userRoles = new HashSet<>();

    public User(){}

    public User(int userId, String username, String password, OffsetDateTime createDate,
                OffsetDateTime updateDate, boolean isEnabled, boolean isAccountNonBlocked,Set<Role> userRoles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.isEnabled = isEnabled;
        this.isAccountNonLocked = isAccountNonBlocked;
        this.userRoles = userRoles;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return userId;
    }

    public void setId(int id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(OffsetDateTime createDate) {
        this.createDate = createDate;
    }

    public OffsetDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(OffsetDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isAccountNonBlocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonBlocked(boolean accountNonBlocked) {
        isAccountNonLocked = accountNonBlocked;
    }

    public Set<Role> getRoles(){
        return userRoles;
    }

    public void setRoles(Set<Role> userRoles){
        this.userRoles = userRoles;
    }



}
