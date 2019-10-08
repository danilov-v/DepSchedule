package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CalendarBaseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-08T17:10:57.427864+03:00[Europe/Minsk]")

public class CalendarBaseDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("shift")
  private Integer shift;

  @JsonProperty("isAstronomical")
  private Boolean isAstronomical;

  public CalendarBaseDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Наименование календаря
   * @return name
  */
  @ApiModelProperty(value = "Наименование календаря")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CalendarBaseDto shift(Integer shift) {
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

  public CalendarBaseDto isAstronomical(Boolean isAstronomical) {
    this.isAstronomical = isAstronomical;
    return this;
  }

  /**
   * Относительно какого времени создаются события? (изначально установлен 'true') * `true` - Астрономическое * `false` - Оперативное 
   * @return isAstronomical
  */
  @ApiModelProperty(value = "Относительно какого времени создаются события? (изначально установлен 'true') * `true` - Астрономическое * `false` - Оперативное ")


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
    CalendarBaseDto calendarBase = (CalendarBaseDto) o;
    return Objects.equals(this.name, calendarBase.name) &&
        Objects.equals(this.shift, calendarBase.shift) &&
        Objects.equals(this.isAstronomical, calendarBase.isAstronomical);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, shift, isAstronomical);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarBaseDto {\n");
    
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

