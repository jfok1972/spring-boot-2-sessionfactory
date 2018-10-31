package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Slf4j
@Service
public class SomeService {

    private final SessionFactory sessionFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public SomeService(EntityManagerFactory entityManagerFactory, EntityManager entityManager) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManager;
    }

    /**
     * --works on spring boot 1.5 with hibernate 5.0, fails this same way on boot 1.5 and hibernate 5.2
     * works on spring boot 2.1 with LocalSessionFactoryBuilder
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void works() {
        Session sessionFromSessionFactory = sessionFactory.getCurrentSession();
        TransactionStatus sessionFactoryTransactionStatus = sessionFromSessionFactory.getTransaction().getStatus();
        log.info("sessionFactoryTransactionStatus {}", sessionFactoryTransactionStatus);

        Session sessionFromEntityManager = entityManager.unwrap(Session.class);
        TransactionStatus entityManagerTransactionStatus = sessionFromEntityManager.getTransaction().getStatus();
        log.info("entityManagerTransactionStatus {}", entityManagerTransactionStatus);

        boolean isSameSession = sessionFactory.getCurrentSession() == entityManager.unwrap(Session.class)
                && sessionFactory.getCurrentSession() == entityManager.unwrap(null)
                && sessionFactory.getCurrentSession() == EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
        log.info("same session instance {}", isSameSession);
    }

}
