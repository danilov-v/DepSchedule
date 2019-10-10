package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * EventPostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-10T11:29:07.772005+03:00[Europe/Minsk]")

public class EventPostDto   {
  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("dateTo")
  private LocalDate dateTo;

  @JsonProperty("location")
  private LocationDto location = null;

  @JsonProperty("planned")
  private Boolean planned = false;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventTypeId")
  private Long eventTypeId;

  public EventPostDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Id подразделения (может ссылаться только на подразделение 4-го уровня)
   * @return unitId
  */
  @ApiModelProperty(required = true, value = "Id подразделения (может ссылаться только на подразделение 4-го уровня)")
  @NotNull


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public EventPostDto dateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  /**
   * Дата начала события
   * @return dateFrom
  */
  @ApiModelProperty(required = true, value = "Дата начала события")
  @NotNull

  @Valid

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public EventPostDto dateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  /**
   * Дата конца события
   * @return dateTo
  */
  @ApiModelProperty(value = "Дата конца события")

  @Valid

  public LocalDate getDateTo() {
    return dateTo;
  }

  public void setDateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
  }

  public EventPostDto location(LocationDto location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * @return location
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public LocationDto getLocation() {
    return location;
  }

  public void setLocation(LocationDto location) {
    this.location = location;
  }

  public EventPostDto planned(Boolean planned) {
    this.planned = planned;
    return this;
  }

  /**
   * Запланированность события
   * @return planned
  */
  @ApiModelProperty(required = true, value = "Запланированность события")
  @NotNull


  public Boolean getPlanned() {
    return planned;
  }

  public void setPlanned(Boolean planned) {
    this.planned = planned;
  }

  public EventPostDto note(String note) {
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

  public EventPostDto eventTypeId(Long eventTypeId) {
    this.eventTypeId = eventTypeId;
    return this;
  }

  /**
   * Ссылка на тип события 
   * @return eventTypeId
  */
  @ApiModelProperty(required = true, value = "Ссылка на тип события ")
  @NotNull


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
    EventPostDto eventPost = (EventPostDto) o;
    return Objects.equals(this.unitId, eventPost.unitId) &&
        Objects.equals(this.dateFrom, eventPost.dateFrom) &&
        Objects.equals(this.dateTo, eventPost.dateTo) &&
        Objects.equals(this.location, eventPost.location) &&
        Objects.equals(this.planned, eventPost.planned) &&
        Objects.equals(this.note, eventPost.note) &&
        Objects.equals(this.eventTypeId, eventPost.eventTypeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, dateFrom, dateTo, location, planned, note, eventTypeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventPostDto {\n");
    
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
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

