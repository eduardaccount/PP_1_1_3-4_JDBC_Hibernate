package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Transaction createTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            createTransaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users_table (" +
                    "user_id INT(10) PRIMARY KEY, " +
                    "user_name VARCHAR(50), " +
                    "user_lastname VARCHAR(50), " +
                    "user_age INT)");
            createTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (createTransaction != null) {
                createTransaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction dropTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            dropTransaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users_table");
            dropTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (dropTransaction != null) {
                dropTransaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction saveTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            saveTransaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            saveTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (saveTransaction != null) {
                saveTransaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction deleteTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            deleteTransaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            deleteTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (deleteTransaction != null) {
                deleteTransaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> results = new ArrayList<>();
        Transaction getUsersTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            getUsersTransaction = session.beginTransaction();
            results = session.createQuery("FROM users_table").list();
            getUsersTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (getUsersTransaction != null) {
                getUsersTransaction.rollback();
            }
        }
        return results;
    }

    @Override
    public void cleanUsersTable() {
        Transaction cleanTransaction = null;
        try (Session session = sessionFactory.openSession()) {
            cleanTransaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users_table").executeUpdate();
            cleanTransaction.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            if (cleanTransaction != null) {
                cleanTransaction.rollback();
            }
        }
    }
}
