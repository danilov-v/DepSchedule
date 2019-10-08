package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * InlineResponse200Dto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-08T17:10:57.427864+03:00[Europe/Minsk]")

public class InlineResponse200Dto   {
  @JsonProperty("active")
  private CalendarBaseReqDto active = null;

  @JsonProperty("calendarList")
  @Valid
  private List<String> calendarList = null;

  public InlineResponse200Dto active(CalendarBaseReqDto active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
  */
  @ApiModelProperty(value = "")

  @Valid

  public CalendarBaseReqDto getActive() {
    return active;
  }

  public void setActive(CalendarBaseReqDto active) {
    this.active = active;
  }

  public InlineResponse200Dto calendarList(List<String> calendarList) {
    this.calendarList = calendarList;
    return this;
  }

  public InlineResponse200Dto addCalendarListItem(String calendarListItem) {
    if (this.calendarList == null) {
      this.calendarList = new ArrayList<>();
    }
    this.calendarList.add(calendarListItem);
    return this;
  }

  /**
   * Get calendarList
   * @return calendarList
  */
  @ApiModelProperty(value = "")


  public List<String> getCalendarList() {
    return calendarList;
  }

  public void setCalendarList(List<String> calendarList) {
    this.calendarList = calendarList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200Dto inlineResponse200 = (InlineResponse200Dto) o;
    return Objects.equals(this.active, inlineResponse200.active) &&
        Objects.equals(this.calendarList, inlineResponse200.calendarList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(active, calendarList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200Dto {\n");
    
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    calendarList: ").append(toIndentedString(calendarList)).append("\n");
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

