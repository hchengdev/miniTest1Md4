package com.example.studentminitest.service;

import com.example.studentminitest.model.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class StudentService implements IStudentService {

    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Student> findAll() {
        String queryStr = "SELECT s FROM Student AS s";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        return query.getResultList();
    }



    @Override
    public void save(Student student) {
            Transaction transaction = null;
            Student origin;
            if (student.getId() == 0) {
                origin = new Student();
            } else {
                origin = findById(student.getId());
            }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setFirstname(student.getFirstname());
            origin.setLastname(student.getLastname());
            origin.setDob(student.getDob());
            origin.setAddress(student.getAddress());
            origin.setMark(student.getMark());
            origin.setImg(student.getImg());
            session.saveOrUpdate(origin);
            transaction.commit();
    } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
    }

    @Override
    public Student findById(int id) {
        String queryStr = "SELECT s FROM Student AS s WHERE s.id = :id";
        TypedQuery<Student> query = entityManager.createQuery(queryStr, Student.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void remove(int id) {
        Student student = findById(id);
        if (student != null) {
            Transaction transaction = null;
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                session.remove(student);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
