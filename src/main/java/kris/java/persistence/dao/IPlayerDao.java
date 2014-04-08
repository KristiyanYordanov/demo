package kris.java.persistence.dao;

import java.util.List;

import kris.java.persistence.model.Player;

public interface IPlayerDao {

    Player findOne(long id);

    List<Player> findAll();

    void create(Player entity);

    Player update(Player entity);

    void delete(Player entity);

    void deleteById(long entityId);

}
