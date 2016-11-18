package com.cpsmi.dao;

import com.cpsmi.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Misha on 13.11.2016.
 */

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User create(User user) {
        entityManager.persist(user);
        return user;
    }


    /**
     * Return all the users stored in the database.
     */
    @SuppressWarnings("unchecked")
    public List getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    /**
     * Return the user having the passed email.
     */
    public User getByEmail(String email) {
        List<User> users = (List<User>) entityManager.createQuery(
                "from User where email = :email")
                .setParameter("email", email)
                .getResultList();
        if (users == null || users.isEmpty()) {
            return null;
        }
        if (users.size() == 1) {//todo убедиться, что в листе один элемент.
            return users.get(0);
        }
        return null;
    }

    /**
     * Return the user having the passed id.
     */
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Update the passed user in the database.
     */
    public void update(User user) {
        entityManager.merge(user);
        return;
    }


}
