package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * CalendarBaseReqDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-29T10:47:52.582273+03:00[Europe/Minsk]")

public class CalendarBaseReqDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("shift")
  private Integer shift;

  @JsonProperty("isAstronomical")
  private Boolean isAstronomical;

  public CalendarBaseReqDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Наименование календаря
   * @return name
  */
  @ApiModelProperty(required = true, value = "Наименование календаря")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CalendarBaseReqDto shift(Integer shift) {
    this.shift = shift;
    return this;
  }

  /**
   * Сдвиг оперативного времени относительно астрономического
   * @return shift
  */
  @ApiModelProperty(required = true, value = "Сдвиг оперативного времени относительно астрономического")
  @NotNull


  public Integer getShift() {
    return shift;
  }

  public void setShift(Integer shift) {
    this.shift = shift;
  }

  public CalendarBaseReqDto isAstronomical(Boolean isAstronomical) {
    this.isAstronomical = isAstronomical;
    return this;
  }

  /**
   * Относительно какого времени создаются события? * `true` - Астрономическое * `false` - Оперативное 
   * @return isAstronomical
  */
  @ApiModelProperty(required = true, value = "Относительно какого времени создаются события? * `true` - Астрономическое * `false` - Оперативное ")
  @NotNull


  public Boolean getIsAstronomical() {
    return isAstronomical;
  }

  public void setIsAstronomical(Boolean isAstronomical) {
    this.isAstronomical = isAstronomical;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CalendarBaseReqDto calendarBaseReq = (CalendarBaseReqDto) o;
    return Objects.equals(this.name, calendarBaseReq.name) &&
        Objects.equals(this.shift, calendarBaseReq.shift) &&
        Objects.equals(this.isAstronomical, calendarBaseReq.isAstronomical);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, shift, isAstronomical);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarBaseReqDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    shift: ").append(toIndentedString(shift)).append("\n");
    sb.append("    isAstronomical: ").append(toIndentedString(isAstronomical)).append("\n");
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

