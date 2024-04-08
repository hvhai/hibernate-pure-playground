package com.codehunter.hibernate_pure_playground.basic_type;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTest {
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
    void persistenceDataWithFormulaMapping() {
        sessionFactory.inTransaction(session -> {
            Account account = new Account();
            account.setId(1L);
            account.setCredit(500.00);
            account.setRate(0.05);
            session.persist(account);
        });

        sessionFactory.inSession(session -> {
            Account account = session.find(Account.class, 1L);
            assertThat(account.getInterest()).isEqualByComparingTo(25.00);
        });
    }
}
