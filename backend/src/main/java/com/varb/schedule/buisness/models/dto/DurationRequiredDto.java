package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * DurationRequiredDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-10T16:43:42.328046+03:00[Europe/Minsk]")

public class DurationRequiredDto   {
  @JsonProperty("duration")
  private Integer duration;

  public DurationRequiredDto duration(Integer duration) {
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
    DurationRequiredDto durationRequired = (DurationRequiredDto) o;
    return Objects.equals(this.duration, durationRequired.duration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(duration);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DurationRequiredDto {\n");
    
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

