package com.cpsmi.dao;

import com.cpsmi.model.PointInQuest;
import com.cpsmi.model.Progress;
import com.cpsmi.model.Quest;
import com.cpsmi.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    public PointInQuest getPreviousQuestion(String email, int questId) {

        List<PointInQuest> points = (List<PointInQuest>) entityManager.createQuery(
                "from PointInQuest as pointInQuest left join fetch pointInQuest.progressList as progress " +
                        "left join fetch progress.user as user " +
                        "where pointInQuest.quest.id = :questId " +
                        "and user.email = :email and progress.end is not null " +
                        "order by pointInQuest.pointOrder desc")
                .setParameter("questId", questId)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        //todo verify number of results
        if (!points.isEmpty()) {
            return points.get(0);
        }
        return null;
    }

    public PointInQuest getCurrentQuestion(String email, int questId) {
        List<PointInQuest> points = (List<PointInQuest>) entityManager.createQuery(
                "from PointInQuest as pointInQuest left join fetch pointInQuest.progressList as progress " +
                        "left join fetch progress.user as user " +
                        "where pointInQuest.quest.id = :questId " +
                        "and user.email = :email and progress.end is null " +
                        "order by pointInQuest.pointOrder desc")
                .setParameter("questId", questId)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        //todo verify number of results
        if (!points.isEmpty()) {
            return points.get(0);
        }
        return null;

    }


    public PointInQuest getNextQuestion(PointInQuest previousQuestion) {
        List<PointInQuest> points = (List<PointInQuest>) entityManager.createQuery(
                "from PointInQuest where quest = :quest and pointOrder = :pointOrder")
                .setParameter("quest", previousQuestion.getQuest())
                .setParameter("pointOrder", previousQuestion.getPointOrder() + 1)
                .setMaxResults(1)
                .getResultList();

        if (points.isEmpty()) {
            return null;
        }
        return points.get(0);

    }


    public PointInQuest getFirstQuestionInQuest(int questId) {
        List<PointInQuest> points = (List<PointInQuest>) entityManager.createQuery(
                "from PointInQuest where quest.id = :questId order by pointOrder")
                .setParameter("questId", questId)
                .setMaxResults(1)
                .getResultList();

        if (points.isEmpty()) {
            return null;
        }
        return points.get(0);
    }

    public int getNumberOfQuestions(int questId) {
        List<PointInQuest> points = (List<PointInQuest>) entityManager.createQuery(
                "from PointInQuest where quest.id = :questId order by pointOrder")
                .setParameter("questId", questId)
                .getResultList();

        if (points.isEmpty()) {
            return 0;
        }
        return points.size();

    }

    public void addProgress(User user, PointInQuest pointInQuest, Date start) { //Записываем в сущность параметры нового прогресса(новые данные в прогрессе юзера)

        Progress progress = new Progress();
        progress.setUser(user);
        progress.setPointInQuest(pointInQuest);
        progress.setStart(start);

        entityManager.persist(progress);
    }

    public boolean userHasProgress(String email, PointInQuest nextQuestion) {
        List progress = entityManager.createQuery(
                "from Progress progress " +
                        "where pointInQuest = :pointInQuest " +
                        "and user.email = :email ")
                .setParameter("pointInQuest", nextQuestion)
                .setParameter("email", email)
                .getResultList();

        return !progress.isEmpty();
    }

    public void progress(PointInQuest pointInQuest, double longitude, double latitude, String email, Date end) {
        Progress progress = (Progress) entityManager.createQuery(
                "from Progress where pointInQuest = :pointInQuest and user.email = :email")
                .setParameter("pointInQuest", pointInQuest)
                .setParameter("email", email)
                .getSingleResult();

        progress.setEnd(end);
        //todo set end lon/lat
        entityManager.persist(progress);
    }

    public List<Progress> getProgressList(String email, int questId) {
        Query rawQuery = entityManager.createQuery(
                "from Progress where pointInQuest.quest.id = :questId and user.email = :email");
        Query query = rawQuery
                .setParameter("questId", questId)
                .setParameter("email", email);
        List<Progress> listProgress = (List<Progress>) query.getResultList();
        return listProgress;
    }
}
