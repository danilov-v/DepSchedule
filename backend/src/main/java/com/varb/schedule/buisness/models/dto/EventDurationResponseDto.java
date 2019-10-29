package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * EventDurationResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-29T10:47:52.582273+03:00[Europe/Minsk]")

public class EventDurationResponseDto   {
  @JsonProperty("duration")
  private Integer duration;

  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("eventTypeId")
  private Long eventTypeId;

  public EventDurationResponseDto duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Длительность события в днях(указывается если длительность по умолчанию не задана либо её надо изменить)
   * minimum: 0
   * maximum: 1000
   * @return duration
  */
  @ApiModelProperty(required = true, value = "Длительность события в днях(указывается если длительность по умолчанию не задана либо её надо изменить)")
  @NotNull

@Min(0) @Max(1000) 
  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public EventDurationResponseDto unitId(Long unitId) {
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

  public EventDurationResponseDto eventTypeId(Long eventTypeId) {
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
    EventDurationResponseDto eventDurationResponse = (EventDurationResponseDto) o;
    return Objects.equals(this.duration, eventDurationResponse.duration) &&
        Objects.equals(this.unitId, eventDurationResponse.unitId) &&
        Objects.equals(this.eventTypeId, eventDurationResponse.eventTypeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(duration, unitId, eventTypeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventDurationResponseDto {\n");
    
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
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

