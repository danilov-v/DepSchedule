package com.varb.schedule.buisness.models.business;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class UnitNode {
    public UnitNode(long id) {
        this.id = id;
    }

    long id;
    List<UnitNode> next;
}
