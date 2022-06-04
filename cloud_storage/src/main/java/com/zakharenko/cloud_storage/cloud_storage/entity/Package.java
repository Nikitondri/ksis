package com.zakharenko.cloud_storage.cloud_storage.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "package")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playlist", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<MyFile> myFileList;

    public Package() {
    }

    public Package(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void addTrack(MyFile myFile){
        if(myFileList == null){
            myFileList = new ArrayList<>();
        }
        myFileList.add(myFile);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MyFile> getTrackList() {
        return myFileList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return id == aPackage.id && Objects.equals(name, aPackage.name) && Objects.equals(user, aPackage.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
