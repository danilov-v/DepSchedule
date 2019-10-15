package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.DurationRequiredDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * EventDurationPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-15T16:45:25.018609+03:00[Europe/Minsk]")

public class EventDurationPutDto   {
  @JsonProperty("duration")
  private Integer duration;

  public EventDurationPutDto duration(Integer duration) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventDurationPutDto eventDurationPut = (EventDurationPutDto) o;
    return Objects.equals(this.duration, eventDurationPut.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventDurationPutDto {\n");
    
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
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

