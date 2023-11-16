package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sesFact = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sesFact.openSession()) {
            transaction = session.beginTransaction();
            String request = """
                    CREATE TABLE IF NOT EXISTS `Users` (
                          `id` BIGINT AUTO_INCREMENT NOT NULL,
                          `name` VARCHAR(50) NULL,
                          `lastName` VARCHAR(50) NULL,
                          `age` TINYINT NOT NULL,
                          PRIMARY KEY (`ID`)
                    );""";
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sesFact.openSession()) {
            transaction = session.beginTransaction();
            String request = "DROP TABLE IF EXISTS `Users`;";
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sesFact.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sesFact.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sesFact.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> result = query.getResultList();
            return result;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sesFact.openSession()) {
            transaction = session.beginTransaction();
            String request = "TRUNCATE TABLE `Users`;";
            session.createSQLQuery(request).executeUpdate();
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
