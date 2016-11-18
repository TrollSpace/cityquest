package com.cpsmi.dao;

import com.cpsmi.model.Point;
import com.cpsmi.model.Progress;
import com.cpsmi.model.Quest;
import com.cpsmi.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Misha on 13.11.2016.
 */

@Repository
@Transactional
public class QuestDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Quest> list() {
        return (List<Quest>) entityManager.createQuery("from Quest").getResultList();
    }

    public Point getNextQuestion(String email, int questId) {
        List<Point> points = (List<Point>) entityManager.createQuery(
                "from Point where quest.id = :questId " +
                        "" +
                        "order by pointOrder desc")
                .setParameter("questId", questId)
                .setMaxResults(1)
                .getResultList();
        return points.get(0);
    }

    public Point getFirstQuestion(int questId) { //
        List<Point> points = (List<Point>) entityManager.createQuery(
                "from Point where quest.id = :questId order by pointOrder")
                .setParameter("questId", questId)
                .setMaxResults(1)
                .getResultList();

        if (points.isEmpty()) {
            return null;
        }
        return points.get(0);

    }

    public void addProgress(User user, Point point, Date start) { //Записываем в сущность параметры нового прогресса(новые данные в прогрессе юзера)

        Progress progress = new Progress();
        progress.setUser(user);
        progress.setStart(start);
        progress.setPoint(point);

        entityManager.persist(progress);
    }
}
