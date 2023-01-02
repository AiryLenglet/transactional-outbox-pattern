package me.lenglet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import me.lenglet.entity.Event;
import org.springframework.transaction.annotation.Transactional;

public class EventConsumer {

    private final EntityManager entityManager;

    public EventConsumer(
            EntityManager entityManager
    ) {
        this.entityManager = entityManager;
    }

    @Transactional
    public boolean consume() {
        try {
            final var event = (Event) this.entityManager.createNativeQuery("""
                            SELECT
                                *
                            FROM
                                event
                            FOR UPDATE SKIP LOCKED
                            FETCH FIRST 1 ROWS ONLY
                            """, Event.class)
                    .getSingleResult();
            process(event);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    private void process(Event event) {
        try {
            doSomething();
            this.entityManager.remove(event);

        } catch (Exception e) {
            event.incrementRetry();
        }
    }

    private void doSomething() {

    }
}
