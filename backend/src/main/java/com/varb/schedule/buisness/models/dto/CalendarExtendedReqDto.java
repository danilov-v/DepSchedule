package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * CalendarExtendedReqDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-10T11:29:07.772005+03:00[Europe/Minsk]")

public class CalendarExtendedReqDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("shift")
  private Integer shift;

  @JsonProperty("isAstronomical")
  private Boolean isAstronomical;

  public CalendarExtendedReqDto name(String name) {
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

  public CalendarExtendedReqDto shift(Integer shift) {
    this.shift = shift;
    return this;
  }

  /**
   * Сдвиг оперативного времени относительно астрономического (изначально установлен '0')
   * @return shift
  */
  @ApiModelProperty(value = "Сдвиг оперативного времени относительно астрономического (изначально установлен '0')")


  public Integer getShift() {
    return shift;
  }

  public void setShift(Integer shift) {
    this.shift = shift;
  }

  public CalendarExtendedReqDto isAstronomical(Boolean isAstronomical) {
    this.isAstronomical = isAstronomical;
    return this;
  }

  /**
   * Относительно какого времени создаются события? (изначально установлен 'true') * `true` - Астрономическое * `false` - Оперативное 
   * @return isAstronomical
  */
  @ApiModelProperty(required = true, value = "Относительно какого времени создаются события? (изначально установлен 'true') * `true` - Астрономическое * `false` - Оперативное ")
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
    CalendarExtendedReqDto calendarExtendedReq = (CalendarExtendedReqDto) o;
    return Objects.equals(this.name, calendarExtendedReq.name) &&
        Objects.equals(this.shift, calendarExtendedReq.shift) &&
        Objects.equals(this.isAstronomical, calendarExtendedReq.isAstronomical);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, shift, isAstronomical);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarExtendedReqDto {\n");
    
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

