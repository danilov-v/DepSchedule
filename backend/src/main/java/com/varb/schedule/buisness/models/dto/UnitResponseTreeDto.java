package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Узел дерева. Содержит информацию о подразделении
 */
@ApiModel(description = "Узел дерева. Содержит информацию о подразделении")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-31T10:54:54.833246+03:00[Europe/Minsk]")

public class UnitResponseTreeDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("parentId")
  private Long parentId;

  @JsonProperty("unitLevel")
  private Integer unitLevel;

  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("events")
  @Valid
  private List<EventResponseDto> events = null;

  @JsonProperty("childUnit")
  @Valid
  private List<UnitResponseTreeDto> childUnit = null;

  public UnitResponseTreeDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Название подразделения
   * @return title
  */
  @ApiModelProperty(value = "Название подразделения")

@Size(min=2) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public UnitResponseTreeDto unitLevel(Integer unitLevel) {
    this.unitLevel = unitLevel;
    return this;
  }

  /**
   * Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение 
   * @return unitLevel
  */
  @ApiModelProperty(value = "Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение ")


  public Integer getUnitLevel() {
    return unitLevel;
  }

  public void setUnitLevel(Integer unitLevel) {
    this.unitLevel = unitLevel;
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
        Objects.equals(this.parentId, unitResponseTree.parentId) &&
        Objects.equals(this.unitLevel, unitResponseTree.unitLevel) &&
        Objects.equals(this.unitId, unitResponseTree.unitId) &&
        Objects.equals(this.events, unitResponseTree.events) &&
        Objects.equals(this.childUnit, unitResponseTree.childUnit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, parentId, unitLevel, unitId, events, childUnit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseTreeDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    unitLevel: ").append(toIndentedString(unitLevel)).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    events: ").append(toIndentedString(events)).append("\n");
    sb.append("    childUnit: ").append(toIndentedString(childUnit)).append("\n");
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

