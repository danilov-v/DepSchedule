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
 * EventBaseReqDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-01T12:32:14.950960+03:00[Europe/Minsk]")

public class EventBaseReqDto   {
  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventTypeId")
  private Long eventTypeId;

  public EventBaseReqDto unitId(Long unitId) {
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

  public EventBaseReqDto dateFrom(LocalDate dateFrom) {
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

  public EventBaseReqDto note(String note) {
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

  public EventBaseReqDto eventTypeId(Long eventTypeId) {
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
    EventBaseReqDto eventBaseReq = (EventBaseReqDto) o;
    return Objects.equals(this.unitId, eventBaseReq.unitId) &&
        Objects.equals(this.dateFrom, eventBaseReq.dateFrom) &&
        Objects.equals(this.note, eventBaseReq.note) &&
        Objects.equals(this.eventTypeId, eventBaseReq.eventTypeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, dateFrom, note, eventTypeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventBaseReqDto {\n");
    
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
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

