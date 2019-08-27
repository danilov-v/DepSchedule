package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Embeddable
public class EventDurationPK implements Serializable {

    public EventDurationPK(Long unitId, Long eventTypeId) {
        this.unitId = unitId;
        this.eventTypeId = eventTypeId;
    }

    @Column(name = "UNIT_ID", nullable = false)
    private Long unitId;

    @Column(nullable = false)
    private Long eventTypeId;

}
