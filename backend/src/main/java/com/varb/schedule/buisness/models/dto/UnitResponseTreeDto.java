package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.dto.UnitBaseReqDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * Узел дерева. Содержит информацию о подразделении
 */
@ApiModel(description = "Узел дерева. Содержит информацию о подразделении")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-24T12:01:31.603414+03:00[Europe/Minsk]")

public class UnitResponseTreeDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("flag")
  private String flag;

  @JsonProperty("planned")
  private Boolean planned = false;

  @JsonProperty("calendarId")
  private Long calendarId;

  @JsonProperty("parentId")
  private Long parentId;

  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("events")
  @Valid
  private List<EventResponseDto> events = null;

  @JsonProperty("childUnit")
  @Valid
  private List<UnitResponseTreeDto> childUnit = null;

  @JsonProperty("eventDuration")
  @Valid
  private Map<String, Integer> eventDuration = null;

  public UnitResponseTreeDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Название подразделения
   * @return title
  */
  @ApiModelProperty(required = true, value = "Название подразделения")
  @NotNull

@Size(min=2) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UnitResponseTreeDto flag(String flag) {
    this.flag = flag;
    return this;
  }

  /**
   * Путь к изображению флага
   * @return flag
  */
  @ApiModelProperty(value = "Путь к изображению флага")


  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public UnitResponseTreeDto planned(Boolean planned) {
    this.planned = planned;
    return this;
  }

  /**
   * Планируемое подразделение? (true/false)
   * @return planned
  */
  @ApiModelProperty(value = "Планируемое подразделение? (true/false)")


  public Boolean getPlanned() {
    return planned;
  }

  public void setPlanned(Boolean planned) {
    this.planned = planned;
  }

  public UnitResponseTreeDto calendarId(Long calendarId) {
    this.calendarId = calendarId;
    return this;
  }

  /**
   * Ссылка на календарь
   * @return calendarId
  */
  @ApiModelProperty(required = true, value = "Ссылка на календарь")
  @NotNull


  public Long getCalendarId() {
    return calendarId;
  }

  public void setCalendarId(Long calendarId) {
    this.calendarId = calendarId;
  }

  public UnitResponseTreeDto parentId(Long parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!) 
   * @return parentId
  */
  @ApiModelProperty(value = "Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!) ")


  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public UnitResponseTreeDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Get unitId
   * @return unitId
  */
  @ApiModelProperty(value = "")


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public UnitResponseTreeDto events(List<EventResponseDto> events) {
    this.events = events;
    return this;
  }

  public UnitResponseTreeDto addEventsItem(EventResponseDto eventsItem) {
    if (this.events == null) {
      this.events = new ArrayList<>();
    }
    this.events.add(eventsItem);
    return this;
  }

  /**
   * Get events
   * @return events
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<EventResponseDto> getEvents() {
    return events;
  }

  public void setEvents(List<EventResponseDto> events) {
    this.events = events;
  }

  public UnitResponseTreeDto childUnit(List<UnitResponseTreeDto> childUnit) {
    this.childUnit = childUnit;
    return this;
  }

  public UnitResponseTreeDto addChildUnitItem(UnitResponseTreeDto childUnitItem) {
    if (this.childUnit == null) {
      this.childUnit = new ArrayList<>();
    }
    this.childUnit.add(childUnitItem);
    return this;
  }

  /**
   * Get childUnit
   * @return childUnit
  */
  @ApiModelProperty(value = "")

  @Valid

  public List<UnitResponseTreeDto> getChildUnit() {
    return childUnit;
  }

  public void setChildUnit(List<UnitResponseTreeDto> childUnit) {
    this.childUnit = childUnit;
  }

  public UnitResponseTreeDto eventDuration(Map<String, Integer> eventDuration) {
    this.eventDuration = eventDuration;
    return this;
  }

  public UnitResponseTreeDto putEventDurationItem(String key, Integer eventDurationItem) {
    if (this.eventDuration == null) {
      this.eventDuration = new HashMap<>();
    }
    this.eventDuration.put(key, eventDurationItem);
    return this;
  }

  /**
   * key - eventDurationType, value - duration
   * @return eventDuration
  */
  @ApiModelProperty(value = "key - eventDurationType, value - duration")


  public Map<String, Integer> getEventDuration() {
    return eventDuration;
  }

  public void setEventDuration(Map<String, Integer> eventDuration) {
    this.eventDuration = eventDuration;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnitResponseTreeDto unitResponseTree = (UnitResponseTreeDto) o;
    return Objects.equals(this.title, unitResponseTree.title) &&
        Objects.equals(this.flag, unitResponseTree.flag) &&
        Objects.equals(this.planned, unitResponseTree.planned) &&
        Objects.equals(this.calendarId, unitResponseTree.calendarId) &&
        Objects.equals(this.parentId, unitResponseTree.parentId) &&
        Objects.equals(this.unitId, unitResponseTree.unitId) &&
        Objects.equals(this.events, unitResponseTree.events) &&
        Objects.equals(this.childUnit, unitResponseTree.childUnit) &&
        Objects.equals(this.eventDuration, unitResponseTree.eventDuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, flag, planned, calendarId, parentId, unitId, events, childUnit, eventDuration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseTreeDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    planned: ").append(toIndentedString(planned)).append("\n");
    sb.append("    calendarId: ").append(toIndentedString(calendarId)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    events: ").append(toIndentedString(events)).append("\n");
    sb.append("    childUnit: ").append(toIndentedString(childUnit)).append("\n");
    sb.append("    eventDuration: ").append(toIndentedString(eventDuration)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

