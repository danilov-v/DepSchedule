package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class EventDuration {
    @EmbeddedId
    private EventDurationPK compositePK;

    /**
     * Продолжительность события(в днях)
     */
    private Integer duration;

}
