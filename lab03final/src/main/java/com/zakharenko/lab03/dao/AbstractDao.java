package com.zakharenko.lab03.dao;

import com.zakharenko.lab03.dao.exception.DaoException;
import com.zakharenko.lab03.entity.Playlist;
import com.zakharenko.lab03.entity.Role;
import com.zakharenko.lab03.entity.Track;
import com.zakharenko.lab03.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class AbstractDao<T> implements Dao<T> {
    private Class<T> clazz;

    private final SessionFactory sessionFactory;

    public AbstractDao() {
        this.sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Playlist.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Track.class)
                .addAnnotatedClass(Role.class)
                .buildSessionFactory();
    }

    public final void setClazz(final Class<T> clazzToSet) {
        clazz = clazzToSet;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DaoException {
        Session session = getCurrentSession();
        session.beginTransaction();
        List<T> list = session.createQuery("FROM " + clazz.getName()).list();
        session.getTransaction().commit();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> createQuery(String query) throws DaoException {
        Session session = getCurrentSession();
        session.beginTransaction();
        List<T> list = session.createQuery(query).list();
        session.getTransaction().commit();
        return list;
    }

    @Override
    public T findOne(long id) throws DaoException {
        Session session = getCurrentSession();
        session.beginTransaction();
        T ob = session.get(clazz, id);
        session.getTransaction().commit();
        return ob;
    }

    @Override
    public T create(T entity) throws DaoException {
        if(entity == null){
            throw new DaoException();
        }
        Session session = getCurrentSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T update(T entity) throws DaoException {
        if(entity == null){
            throw new DaoException();
        }
        Session session = getCurrentSession();
        session.beginTransaction();
        T ob = (T) session.merge(entity);
        session.getTransaction().commit();
        return ob;
    }

    @Override
    public void delete(T entity) throws DaoException {
        if(entity == null){
            throw new DaoException();
        }
        Session session = getCurrentSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }

    @Override
    public void deleteById(long id) throws DaoException {
        final T entity = findOne(id);
        if(entity == null){
            throw new DaoException();
        }
        delete(entity);
    }

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
