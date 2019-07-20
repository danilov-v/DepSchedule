package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Embeddable
public class EventDurationPK implements Serializable {

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private String eventType;

}
