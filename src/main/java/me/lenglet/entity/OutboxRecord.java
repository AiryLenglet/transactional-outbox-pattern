package me.lenglet.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class OutboxRecord {

    private int retry = 0;

    public void incrementRetry() {
        this.retry++;
    }
}
