package com.zakharenko.cloud_storage.cloud_storage.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file")
public class MyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private int duration;

//    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @Column(name = "path")
    private String path;

    public MyFile() {
    }

    public MyFile(String name, int duration, Package playlist, String path) {
        this.name = name;
        this.duration = duration;
        this.aPackage = playlist;
        this.path = path;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Package getPackage() {
        return aPackage;
    }

    public void setPlaylist(Package playlist) {
        this.aPackage = playlist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyFile myFile = (MyFile) o;
        return id == myFile.id && duration == myFile.duration && Objects.equals(name, myFile.name) && Objects.equals(aPackage, myFile.aPackage) && Objects.equals(path, myFile.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, aPackage, path);
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", playlist=" + aPackage +
                ", path='" + path + '\'' +
                '}';
    }
}
