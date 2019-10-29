package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * UnitBaseReqDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-29T10:47:52.582273+03:00[Europe/Minsk]")

public class UnitBaseReqDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("flag")
  private String flag;

  @JsonProperty("planned")
  private Boolean planned = false;

  @JsonProperty("calendarId")
  private Long calendarId;

  @JsonProperty("parentId")
  private Long parentId;

  public UnitBaseReqDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Название подразделения
   * @return title
  */
  @ApiModelProperty(required = true, value = "Название подразделения")
  @NotNull

@Size(min=2) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UnitBaseReqDto flag(String flag) {
    this.flag = flag;
    return this;
  }

  /**
   * Путь к изображению флага
   * @return flag
  */
  @ApiModelProperty(value = "Путь к изображению флага")


  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public UnitBaseReqDto planned(Boolean planned) {
    this.planned = planned;
    return this;
  }

  /**
   * Планируемое подразделение? (true/false)
   * @return planned
  */
  @ApiModelProperty(value = "Планируемое подразделение? (true/false)")


  public Boolean getPlanned() {
    return planned;
  }

  public void setPlanned(Boolean planned) {
    this.planned = planned;
  }

  public UnitBaseReqDto calendarId(Long calendarId) {
    this.calendarId = calendarId;
    return this;
  }

  /**
   * Ссылка на календарь
   * @return calendarId
  */
  @ApiModelProperty(required = true, value = "Ссылка на календарь")
  @NotNull


  public Long getCalendarId() {
    return calendarId;
  }

  public void setCalendarId(Long calendarId) {
    this.calendarId = calendarId;
  }

  public UnitBaseReqDto parentId(Long parentId) {
    this.parentId = parentId;
    return this;
  }

  /**
   * Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!) 
   * @return parentId
  */
  @ApiModelProperty(value = "Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!) ")


  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnitBaseReqDto unitBaseReq = (UnitBaseReqDto) o;
    return Objects.equals(this.title, unitBaseReq.title) &&
        Objects.equals(this.flag, unitBaseReq.flag) &&
        Objects.equals(this.planned, unitBaseReq.planned) &&
        Objects.equals(this.calendarId, unitBaseReq.calendarId) &&
        Objects.equals(this.parentId, unitBaseReq.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, flag, planned, calendarId, parentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitBaseReqDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    planned: ").append(toIndentedString(planned)).append("\n");
    sb.append("    calendarId: ").append(toIndentedString(calendarId)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
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

