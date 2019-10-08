package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Objects;

/**
 * EventPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-08T17:10:57.427864+03:00[Europe/Minsk]")

public class EventPutDto   {
  @JsonProperty("duration")
  private Integer duration;

  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("location")
  private LocationDto location = null;

  @JsonProperty("planned")
  private Boolean planned;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventTypeId")
  private Long eventTypeId;

  public EventPutDto duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Длительность события в днях(указывается если длительность по умолчанию не задана либо её надо изменить)
   * minimum: 0
   * maximum: 1000
   * @return duration
  */
  @ApiModelProperty(value = "Длительность события в днях(указывается если длительность по умолчанию не задана либо её надо изменить)")

@Min(0) @Max(1000) 
  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public EventPutDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Id подразделения (может ссылаться только на подразделение 4-го уровня)
   * @return unitId
  */
  @ApiModelProperty(value = "Id подразделения (может ссылаться только на подразделение 4-го уровня)")


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public EventPutDto dateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  /**
   * Дата начала события
   * @return dateFrom
  */
  @ApiModelProperty(value = "Дата начала события")

  @Valid

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public EventPutDto location(LocationDto location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
  */
  @ApiModelProperty(value = "")

  @Valid

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public EventPutDto planned(Boolean planned) {
    this.planned = planned;
    return this;
  }

  /**
   * Запланированность события
   * @return planned
  */
  @ApiModelProperty(value = "Запланированность события")


  public Boolean getPlanned() {
    return planned;
  }

  public void setPlanned(Boolean planned) {
    this.planned = planned;
  }

  public EventPutDto note(String note) {
    this.note = note;
    return this;
  }

  /**
   * Примечание
   * @return note
  */
  @ApiModelProperty(value = "Примечание")


  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public EventPutDto eventTypeId(Long eventTypeId) {
    this.eventTypeId = eventTypeId;
    return this;
  }

  /**
   * Ссылка на тип события 
   * @return eventTypeId
  */
  @ApiModelProperty(value = "Ссылка на тип события ")


  public Long getEventTypeId() {
    return eventTypeId;
  }

  public void setEventTypeId(Long eventTypeId) {
    this.eventTypeId = eventTypeId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventPutDto eventPut = (EventPutDto) o;
    return Objects.equals(this.duration, eventPut.duration) &&
        Objects.equals(this.unitId, eventPut.unitId) &&
        Objects.equals(this.dateFrom, eventPut.dateFrom) &&
        Objects.equals(this.location, eventPut.location) &&
        Objects.equals(this.planned, eventPut.planned) &&
        Objects.equals(this.note, eventPut.note) &&
        Objects.equals(this.eventTypeId, eventPut.eventTypeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(duration, unitId, dateFrom, location, planned, note, eventTypeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventPutDto {\n");
    
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    planned: ").append(toIndentedString(planned)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    eventTypeId: ").append(toIndentedString(eventTypeId)).append("\n");
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

