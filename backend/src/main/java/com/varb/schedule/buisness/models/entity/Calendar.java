package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Accessors(chain = true)
@Entity
public class Calendar {

    public Calendar() {
    }

    public Calendar(Calendar that){
        name = that.getName();
        shift = that.getShift();
        isAstronomical = that.isAstronomical();
    }

    @Id
    private String name;

    @Column(nullable = false)
    private int shift;

    @Column(nullable = false)
    private boolean isAstronomical;

}
