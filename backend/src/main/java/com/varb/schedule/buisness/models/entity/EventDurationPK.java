package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Embeddable
public class EventDurationPK implements Serializable {

    public EventDurationPK(Long unitId, Long eventTypeId) {
        this.unitId = unitId;
        this.eventTypeId = eventTypeId;
    }

    public EventDurationPK() {
    }

    @Column(nullable = false)
    private Long unitId;

    @Column(nullable = false)
    private Long eventTypeId;

}
