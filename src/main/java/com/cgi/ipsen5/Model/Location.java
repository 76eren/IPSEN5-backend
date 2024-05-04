package com.cgi.ipsen5.Model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Location {
    private UUID id;
    private UUID wingId;

    private String name;
    private String type;
    private int capacity;
    private boolean multireservable;
    private LocalDateTime createdAt;
}
