package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * EventRecentResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-24T12:01:31.603414+03:00[Europe/Minsk]")

public class EventRecentResponseDto   {
  @JsonProperty("eventId")
  private Long eventId;

  @JsonProperty("unitTitle")
  private String unitTitle;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("dateTo")
  private LocalDate dateTo;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventTypeDescription")
  private String eventTypeDescription;

  @JsonProperty("calendarId")
  private Long calendarId;

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

  public EventRecentResponseDto calendarId(Long calendarId) {
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
        Objects.equals(this.note, eventRecentResponse.note) &&
        Objects.equals(this.eventTypeDescription, eventRecentResponse.eventTypeDescription) &&
        Objects.equals(this.calendarId, eventRecentResponse.calendarId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventId, unitTitle, dateFrom, dateTo, note, eventTypeDescription, calendarId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventRecentResponseDto {\n");
    
    sb.append("    eventId: ").append(toIndentedString(eventId)).append("\n");
    sb.append("    unitTitle: ").append(toIndentedString(unitTitle)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    eventTypeDescription: ").append(toIndentedString(eventTypeDescription)).append("\n");
    sb.append("    calendarId: ").append(toIndentedString(calendarId)).append("\n");
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

