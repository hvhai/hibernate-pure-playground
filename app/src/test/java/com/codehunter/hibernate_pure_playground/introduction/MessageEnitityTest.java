package com.codehunter.hibernate_pure_playground.introduction;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageEnitityTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        final StandardServiceRegistry registry
                = new StandardServiceRegistryBuilder()
                        .configure("introduction-hibernate.cfg.xml")
                        .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    void createMessageTest() {
        MessageEntity message = new MessageEntity();
        String text = "Hello hibernate";
        message.setText(text);

        try (Session session = sessionFactory.openSession();) {
            Transaction tx = session.beginTransaction();
            session.persist(message);
            tx.commit();
        }

        assertThat(message.getId()).isEqualByComparingTo(1L);
        assertThat(message.getText()).isEqualTo(text);
    }

    @Test
    void readMessageTest() {
        createMessageTest();
        try (Session session = sessionFactory.openSession();) {
            List<MessageEntity> resultList
                    = session.createQuery("FROM Message", MessageEntity.class)
                            .list();
            assertThat(resultList).hasSize(1);
            assertThat(resultList.get(0).getId()).isEqualTo(1L);
        }
    }

    @Test
    void readMessageInFuntionalWayTest() {
        createMessageTest();
        sessionFactory.inSession(session -> {
            List<MessageEntity> resultList
                    = session.createSelectionQuery("FROM Message", MessageEntity.class)
                            .getResultList();
            assertThat(resultList).hasSize(1);
            assertThat(resultList.get(0).getId()).isEqualTo(1L);
        });
    }

}
