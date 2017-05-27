package com.example.pawel.championsscore.model.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by Mateusz on 06.05.2017.
 */
@Entity
public class Competition {
    @Id
    private long id;
    private String name;
    private String flagUrl;

    @Generated(hash = 498404120)
    public Competition(long id, String name, String flagUrl) {
        this.id = id;
        this.name = name;
        this.flagUrl = flagUrl;
    }

    @Generated(hash = 61334593)
    public Competition() {
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

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }


    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flagUrl='" + flagUrl + '\'' +
                '}';
    }
}
