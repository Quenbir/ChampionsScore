package com.example.pawel.championsscore.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Mateusz on 06.05.2017.
 */

@Entity
public class Round {
    @Id
    private long id;
    private String name;
    @ToOne(joinProperty = "id")
    @NotNull
    private Competition competition;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 225770241)
    private transient RoundDao myDao;
    @Generated(hash = 143667786)
    public Round(long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1453571562)
    public Round() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Generated(hash = 583425782)
    private transient Long competition__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1532181063)
    public Competition getCompetition() {
        long __key = this.id;
        if (competition__resolvedKey == null
                || !competition__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CompetitionDao targetDao = daoSession.getCompetitionDao();
            Competition competitionNew = targetDao.load(__key);
            synchronized (this) {
                competition = competitionNew;
                competition__resolvedKey = __key;
            }
        }
        return competition;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1192816621)
    public void setCompetition(@NotNull Competition competition) {
        if (competition == null) {
            throw new DaoException(
                    "To-one property 'id' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.competition = competition;
            id = competition.getId();
            competition__resolvedKey = id;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1377654301)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRoundDao() : null;
    }
}
