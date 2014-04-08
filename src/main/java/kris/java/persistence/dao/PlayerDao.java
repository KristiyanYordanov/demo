package kris.java.persistence.dao;

import kris.java.persistence.model.Player;

import org.springframework.stereotype.Repository;

@Repository
public class PlayerDao extends AbstractJpaDAO<Player> implements IPlayerDao {

    public PlayerDao() {
        super();

        setClazz(Player.class);
    }

    // API

}
