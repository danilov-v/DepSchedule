package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

/**
 * EventRecentResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-11T11:37:06.077592+03:00[Europe/Minsk]")

public class EventRecentResponseDto   {
  @JsonProperty("eventId")
  private Long eventId;

  @JsonProperty("unitTitle")
  private String unitTitle;

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

  @JsonProperty("eventTypeDescription")
  private String eventTypeDescription;

  public EventRecentResponseDto eventId(Long eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * Get eventId
   * @return eventId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getEventId() {
    return eventId;
  }

  public void setEventId(Long eventId) {
    this.eventId = eventId;
  }

  public EventRecentResponseDto unitTitle(String unitTitle) {
    this.unitTitle = unitTitle;
    return this;
  }

  /**
   * Название подразделения
   * @return unitTitle
  */
  @ApiModelProperty(value = "Название подразделения")

@Size(min=2) 
  public String getUnitTitle() {
    return unitTitle;
  }

  public void setUnitTitle(String unitTitle) {
    this.unitTitle = unitTitle;
  }

  public EventRecentResponseDto dateFrom(LocalDate dateFrom) {
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

  public EventRecentResponseDto dateTo(LocalDate dateTo) {
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

  public EventRecentResponseDto location(LocationDto location) {
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

  public EventRecentResponseDto planned(Boolean planned) {
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

  public EventRecentResponseDto note(String note) {
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

  public EventRecentResponseDto eventTypeDescription(String eventTypeDescription) {
    this.eventTypeDescription = eventTypeDescription;
    return this;
  }

  /**
   * Описание события
   * @return eventTypeDescription
  */
  @ApiModelProperty(value = "Описание события")

@Size(min=3) 
  public String getEventTypeDescription() {
    return eventTypeDescription;
  }

  public void setEventTypeDescription(String eventTypeDescription) {
    this.eventTypeDescription = eventTypeDescription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventRecentResponseDto eventRecentResponse = (EventRecentResponseDto) o;
    return Objects.equals(this.eventId, eventRecentResponse.eventId) &&
        Objects.equals(this.unitTitle, eventRecentResponse.unitTitle) &&
        Objects.equals(this.dateFrom, eventRecentResponse.dateFrom) &&
        Objects.equals(this.dateTo, eventRecentResponse.dateTo) &&
        Objects.equals(this.location, eventRecentResponse.location) &&
        Objects.equals(this.planned, eventRecentResponse.planned) &&
        Objects.equals(this.note, eventRecentResponse.note) &&
        Objects.equals(this.eventTypeDescription, eventRecentResponse.eventTypeDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, unitTitle, dateFrom, dateTo, location, planned, note, eventTypeDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventRecentResponseDto {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    unitTitle: ").append(toIndentedString(unitTitle)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    planned: ").append(toIndentedString(planned)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    eventTypeDescription: ").append(toIndentedString(eventTypeDescription)).append("\n");
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

