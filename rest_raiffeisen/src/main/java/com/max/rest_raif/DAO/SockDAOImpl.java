package com.max.rest_raif.DAO;

import com.max.rest_raif.entity.Sock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SockDAOImpl implements SockDAO {

    final SessionFactory sessionFactory;

    @Autowired
    public SockDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public long getQuantity(String color, String operation, byte cottonPart) {
        Session session = sessionFactory.getCurrentSession();
        String znak = null;
        switch (operation) {
            case "moreThan":
                znak = ">";
                break;
            case "lessThan":
                znak = "<";
                break;
            case "equals":
                znak = "=";
                break;
        }
        Query query = session.createQuery(
                "select sum(sock.quantity) " +
                        "from Sock sock where color = :color " +
                        "and cottonPart " +
                         znak + " :cottonPart");
        query.setParameter("color", color);
        query.setParameter("cottonPart", cottonPart);
        return (long) query.getResultList().get(0);
    }

    @Override
    @Transactional
    public void addSock(Sock sock) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select 1 from Sock " +
                "where exists (select 1 from Sock p " +
                "where color = :color and cottonPart = :cotton)");
        query.setParameter("color", sock.getColor());
        query.setParameter("cotton", sock.getCottonPart());
        boolean exists = query.uniqueResult() != null;


        if (exists){
            Query updateQuery = session.createQuery(
                    "update Sock set quantity = quantity + :quantity " +
                    "where color = :color and cottonPart = :cottonPart");
            updateQuery.setParameter("quantity", sock.getQuantity());
            updateQuery.setParameter("color", sock.getColor());
            updateQuery.setParameter("cottonPart", sock.getCottonPart());
            updateQuery.executeUpdate();
        }else {
            session.save(sock);
        }

    }

    @Override
    @Transactional
    public void takeSock(Sock sock) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select 1 from Sock " +
                "where exists (select 1 from Sock p " +
                "where color = :color and cottonPart = :cotton)");
        query.setParameter("color", sock.getColor());
        query.setParameter("cotton", sock.getCottonPart());
        boolean exists = query.uniqueResult() != null;


        if (exists){
            Query updateQuery = session.createQuery(
                    "update Sock set quantity = quantity - :quantity " +
                            "where color = :color and cottonPart = :cottonPart");
            updateQuery.setParameter("quantity", sock.getQuantity());
            updateQuery.setParameter("color", sock.getColor());
            updateQuery.setParameter("cottonPart", sock.getCottonPart());
            updateQuery.executeUpdate();
        }else {
            session.save(sock);
        }

    }


    //метод для вывода списка всех носков(в задании не требуется)
    @Transactional
    @Override
    public List<Sock> getAllSocks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Sock", Sock.class).getResultList();

    }
}
