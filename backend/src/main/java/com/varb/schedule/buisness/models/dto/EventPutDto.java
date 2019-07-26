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

/**
 * EventPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-20T13:04:10.026955+03:00[Europe/Minsk]")

public class EventPutDto   {
  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventType")
  private String eventType;

  public EventPutDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Id подразделения (может ссылаться только на подразделение 4-го уровня
   * @return unitId
  */
  @ApiModelProperty(value = "Id подразделения (может ссылаться только на подразделение 4-го уровня")


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

  public EventPutDto eventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  /**
   * Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} 
   * @return eventType
  */
  @ApiModelProperty(value = "Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} ")


  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventPutDto eventPut = (EventPutDto) o;
    return Objects.equals(this.unitId, eventPut.unitId) &&
        Objects.equals(this.dateFrom, eventPut.dateFrom) &&
        Objects.equals(this.note, eventPut.note) &&
        Objects.equals(this.eventType, eventPut.eventType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, dateFrom, note, eventType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventPutDto {\n");
    
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

