package com.codehunter.hibernate_pure_playground.basic_type;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneTest {

    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        final StandardServiceRegistry registry
                = new StandardServiceRegistryBuilder()
                .configure("basic-type-hibernate.cfg.xml")
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
    void insertEnumValue() {
        sessionFactory.inTransaction(session -> {
            Phone phone = new Phone();
            phone.setId(1L);
            phone.setNumber("037123456");
            phone.setType(Phone.PhoneType.MOBILE);
            phone.setColor(Phone.PhoneColorType.RED);
            session.persist(phone);
        });
        sessionFactory.inSession(session -> {
            Phone phone = session.createQuery("FROM Phone WHERE id = 1", Phone.class)
                    .getSingleResult();
            assertThat(phone.getColor()).isEqualByComparingTo(Phone.PhoneColorType.RED);
        });
    }
}
