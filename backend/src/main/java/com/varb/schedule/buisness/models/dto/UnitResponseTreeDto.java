package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Узел дерева. Содержит информацию о подразделении
 */
@ApiModel(description = "Узел дерева. Содержит информацию о подразделении")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-09T17:33:46.060950+03:00[Europe/Minsk]")

public class UnitResponseTreeDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("flag")
  private String flag;

  @JsonProperty("exists")
  private Boolean exists = true;

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

  public UnitResponseTreeDto exists(Boolean exists) {
    this.exists = exists;
    return this;
  }

  /**
   * Планируемое подразделение? (true/false)
   * @return exists
  */
  @ApiModelProperty(value = "Планируемое подразделение? (true/false)")


  public Boolean getExists() {
    return exists;
  }

  public void setExists(Boolean exists) {
    this.exists = exists;
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
        Objects.equals(this.exists, unitResponseTree.exists) &&
        Objects.equals(this.parentId, unitResponseTree.parentId) &&
        Objects.equals(this.unitId, unitResponseTree.unitId) &&
        Objects.equals(this.events, unitResponseTree.events) &&
        Objects.equals(this.childUnit, unitResponseTree.childUnit) &&
        Objects.equals(this.eventDuration, unitResponseTree.eventDuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, flag, exists, parentId, unitId, events, childUnit, eventDuration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseTreeDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    exists: ").append(toIndentedString(exists)).append("\n");
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

