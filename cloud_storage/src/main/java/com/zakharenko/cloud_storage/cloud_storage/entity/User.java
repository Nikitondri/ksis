package com.zakharenko.cloud_storage.cloud_storage.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Package> packageList;

    public User() {}

    public User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public void addPlayList(Package aPackage){
        if(packageList == null){
            packageList = new ArrayList<>();
        }
        packageList.add(aPackage);
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPackageList(List<Package> packageList) {
        this.packageList = packageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(packageList, user.packageList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, packageList);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", packageList=" + packageList +
                '}';
    }
}
