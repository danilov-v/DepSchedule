package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-19T17:00:06.885213+03:00[Europe/Minsk]")

public class EventResponseDto   {
  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("note")
  private String note;

  @JsonProperty("eventType")
  private String eventType;

  @JsonProperty("id")
  private Long id;

  @JsonProperty("dateTo")
  private LocalDate dateTo;

  public EventResponseDto unitId(Long unitId) {
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

  public EventResponseDto dateFrom(LocalDate dateFrom) {
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

  public EventResponseDto note(String note) {
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

  public EventResponseDto eventType(String eventType) {
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

  public EventResponseDto id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public EventResponseDto dateTo(LocalDate dateTo) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventResponseDto eventResponse = (EventResponseDto) o;
    return Objects.equals(this.unitId, eventResponse.unitId) &&
        Objects.equals(this.dateFrom, eventResponse.dateFrom) &&
        Objects.equals(this.note, eventResponse.note) &&
        Objects.equals(this.eventType, eventResponse.eventType) &&
        Objects.equals(this.id, eventResponse.id) &&
        Objects.equals(this.dateTo, eventResponse.dateTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, dateFrom, note, eventType, id, dateTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventResponseDto {\n");
    
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
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

