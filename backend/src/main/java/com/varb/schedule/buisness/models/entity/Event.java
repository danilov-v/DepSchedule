package com.varb.schedule.buisness.models.entity;

import com.varb.schedule.buisness.models.business.LocationTypeEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Data()
@EqualsAndHashCode(exclude = {"eventType", "unit"})
@Accessors(chain = true)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;



    @Setter(AccessLevel.NONE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_TYPE_ID", nullable = false)
    private EventType eventType;

    public Long getEventTypeId(){
        return eventType.getTypeId();
    }
    public Event setEventTypeId(Long typeId){
        eventType = new EventType().setTypeId(typeId);
        return this;
    }



    @Setter(AccessLevel.NONE)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIT_ID", nullable = false)
    private Unit unit;

    public Long getUnitId(){
        return unit.getUnitId();
    }
    public Event setUnitId(Long unitId){
        unit = new Unit().setUnitId(unitId);
        return this;
    }



    @Column(nullable = false)
    private LocalDate dateFrom;

    @Column(nullable = false)
    private LocalDate dateTo;

    @Nullable
    private String note;

    @Column(nullable = false)
    private Boolean planned;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LocationTypeEnum locationType;

}
