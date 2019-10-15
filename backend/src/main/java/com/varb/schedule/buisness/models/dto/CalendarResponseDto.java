package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * CalendarResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-15T13:05:40.298965+03:00[Europe/Minsk]")

public class CalendarResponseDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("shift")
  private Integer shift;

  @JsonProperty("isAstronomical")
  private Boolean isAstronomical;

  @JsonProperty("calendarId")
  private Long calendarId;

  public CalendarResponseDto name(String name) {
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

  public CalendarResponseDto shift(Integer shift) {
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

  public CalendarResponseDto isAstronomical(Boolean isAstronomical) {
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

  public CalendarResponseDto calendarId(Long calendarId) {
    this.calendarId = calendarId;
    return this;
  }

  /**
   * Get calendarId
   * @return calendarId
  */
  @ApiModelProperty(value = "")


  public Long getCalendarId() {
    return calendarId;
  }

  public void setCalendarId(Long calendarId) {
    this.calendarId = calendarId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CalendarResponseDto calendarResponse = (CalendarResponseDto) o;
    return Objects.equals(this.name, calendarResponse.name) &&
        Objects.equals(this.shift, calendarResponse.shift) &&
        Objects.equals(this.isAstronomical, calendarResponse.isAstronomical) &&
        Objects.equals(this.calendarId, calendarResponse.calendarId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, shift, isAstronomical, calendarId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarResponseDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    shift: ").append(toIndentedString(shift)).append("\n");
    sb.append("    isAstronomical: ").append(toIndentedString(isAstronomical)).append("\n");
    sb.append("    calendarId: ").append(toIndentedString(calendarId)).append("\n");
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

