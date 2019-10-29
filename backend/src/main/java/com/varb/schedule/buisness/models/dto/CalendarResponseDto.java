package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * CalendarResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-29T10:47:52.582273+03:00[Europe/Minsk]")

public class CalendarResponseDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("shift")
  private Integer shift;

  @JsonProperty("isAstronomical")
  private Boolean isAstronomical;

  @JsonProperty("calendarId")
  private Long calendarId;

  @JsonProperty("dateFrom")
  private LocalDate dateFrom;

  @JsonProperty("dateTo")
  private LocalDate dateTo;

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

  public CalendarResponseDto dateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  /**
   * Дата начала первого события в календаре
   * @return dateFrom
  */
  @ApiModelProperty(value = "Дата начала первого события в календаре")

  @Valid

  public LocalDate getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(LocalDate dateFrom) {
    this.dateFrom = dateFrom;
  }

  public CalendarResponseDto dateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  /**
   * Дата конца последнего события в календаре
   * @return dateTo
  */
  @ApiModelProperty(value = "Дата конца последнего события в календаре")

  @Valid

  public LocalDate getDateTo() {
    return dateTo;
  }

  public void setDateTo(LocalDate dateTo) {
    this.dateTo = dateTo;
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
        Objects.equals(this.calendarId, calendarResponse.calendarId) &&
        Objects.equals(this.dateFrom, calendarResponse.dateFrom) &&
        Objects.equals(this.dateTo, calendarResponse.dateTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, shift, isAstronomical, calendarId, dateFrom, dateTo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CalendarResponseDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    shift: ").append(toIndentedString(shift)).append("\n");
    sb.append("    isAstronomical: ").append(toIndentedString(isAstronomical)).append("\n");
    sb.append("    calendarId: ").append(toIndentedString(calendarId)).append("\n");
    sb.append("    dateFrom: ").append(toIndentedString(dateFrom)).append("\n");
    sb.append("    dateTo: ").append(toIndentedString(dateTo)).append("\n");
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

