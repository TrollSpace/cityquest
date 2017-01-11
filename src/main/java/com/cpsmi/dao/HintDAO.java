package com.cpsmi.dao;

import com.cpsmi.model.PointInQuest;
import com.cpsmi.model.Progress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Misha on 24.11.2016.
 */
@Repository
@Transactional
public class HintDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void addHintToProgress(Progress progress) {
        entityManager.persist(progress);
    }

    public Progress getNewHint(String email, PointInQuest pointInQuest) {
        List<Progress> progressList = (List<Progress>) entityManager.createQuery(
                "from Progress as progress " +
                        "join fetch progress.pointInQuest as pointInQuest " +
                        "join fetch progress.user as user " +
                        "where user.email = :email " +
                        "and pointInQuest = :pointInQuest")
                .setParameter("pointInQuest", pointInQuest)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        if (!progressList.isEmpty()) {
            return progressList.get(0);
        }
        return null;

    }

}
