package com.cpsmi.dao;

import com.cpsmi.dto.HintDTO;
import com.cpsmi.model.Hint;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Misha on 24.11.2016.
 */
public class HintDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Hint> list() {
        return (List<Hint>) entityManager.createQuery("from Hint").getResultList();
    }

    public HintDTO getNewHint(String email, int questId) {
        List<HintDTO> lastUsedhints = (List<HintDTO>) entityManager.createQuery("from PointInQuest as pointInQuest " +
                "left join fetch pointInQuest.progressList as progress " +
                "where pointInQuest.quest = :questId " +
                "and user.email = :email and progress.lastUsedHintId is null " +
                "order by pointInQuest.pointOrder desc")
                .setParameter("questId", questId)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        if (!lastUsedhints.isEmpty()) {
            return lastUsedhints.get(0);
        }
        return null;


    }

}
