package com.jojotjo.ticketbooking.util;

import java.util.concurrent.atomic.AtomicLong;

public final class IDGenerator {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    private IDGenerator() {
        // utility class – prevent instantiation
    }

    public static long generateId() {
        return COUNTER.getAndIncrement();
    }
}
