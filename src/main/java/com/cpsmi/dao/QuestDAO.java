package com.cpsmi.dao;

import com.cpsmi.model.PointInQuest;
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

    /*Hibernate: select pointinque0_.id as id1_2_0_, progressli1_.id as id1_3_1_, pointinque0_.point_id as point_id3_2_0_, pointinque0_.point_order as point_or2_2_0_, pointinque0_.quest_id as quest_id4_2_0_, progressli1_.end as end2_3_1_, progressli1_.end_latitude as end_lati3_3_1_, progressli1_.end_longitude as end_long4_3_1_, progressli1_.point_in_quest_id as point_in6_3_1_, progressli1_.start as start5_3_1_, progressli1_.user_id as user_id7_3_1_ from point_in_quest pointinque0_ left outer join progress progressli1_ on pointinque0_.id=progressli1_.point_in_quest_id cross join user user2_ where progressli1_.user_id=user2_.id and progressli1_.point_in_quest_id=pointinque0_.id and user2_.email=? and pointinque0_.quest_id=? and (progressli1_.end is null) order by pointinque0_.point_order desc limit ?*/
    @PersistenceContext
    private EntityManager entityManager;

    public List<Quest> list() {
        return (List<Quest>) entityManager.createQuery("from Quest").getResultList();
    }

    public PointInQuest getNextQuestion(String email, int questId) {
        List points = entityManager.createQuery(
                "from PointInQuest as pointInQuest left join pointInQuest.progressList as progress " +
                        "where progress.pointInQuest = pointInQuest " +
                        "and progress.user.email = :email " +
                        "and pointInQuest.quest.id = :questId " +
                        "and progress.end is null " +
                        "order by pointInQuest.pointOrder desc")
                .setParameter("questId", questId)
                .setParameter("email", email)
                .setMaxResults(1)
                .getResultList();
        //todo verify number of results
        if (!points.isEmpty()) {
            return (PointInQuest) ((Object[]) points.get(0))[0];
        }
        return null;
    }

    public PointInQuest getFirstQuestion(int questId) {
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
}
