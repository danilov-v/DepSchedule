package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * EventDurationDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-20T13:04:10.026955+03:00[Europe/Minsk]")

public class EventDurationDto   {
  @JsonProperty("unitId")
  private Long unitId;

  @JsonProperty("eventType")
  private String eventType;

  @JsonProperty("duration")
  private Integer duration;

  public EventDurationDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Get unitId
   * @return unitId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public EventDurationDto eventType(String eventType) {
    this.eventType = eventType;
    return this;
  }

  /**
   * Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} 
   * @return eventType
  */
  @ApiModelProperty(required = true, value = "Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} ")
  @NotNull


  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public EventDurationDto duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  /**
   * время выполнения (в днях)
   * minimum: 1
   * maximum: 1000
   * @return duration
  */
  @ApiModelProperty(required = true, value = "время выполнения (в днях)")
  @NotNull

@Min(1) @Max(1000) 
  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventDurationDto eventDuration = (EventDurationDto) o;
    return Objects.equals(this.unitId, eventDuration.unitId) &&
        Objects.equals(this.eventType, eventDuration.eventType) &&
        Objects.equals(this.duration, eventDuration.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, eventType, duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventDurationDto {\n");
    
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    eventType: ").append(toIndentedString(eventType)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
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

