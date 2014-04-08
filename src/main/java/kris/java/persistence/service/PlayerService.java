package kris.java.persistence.service;

import java.util.List;

import kris.java.persistence.dao.IPlayerDao;
import kris.java.persistence.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("playerService")
@Transactional
public class PlayerService {

    @Autowired
    private IPlayerDao dao;

    public PlayerService() {
        super();
    }

    // API

    public void create(final Player entity) {
        dao.create(entity);
    }

    public Player findOne(final long id) {
        return dao.findOne(id);
    }

    public List<Player> findAll() {
        return dao.findAll();
    }

}
