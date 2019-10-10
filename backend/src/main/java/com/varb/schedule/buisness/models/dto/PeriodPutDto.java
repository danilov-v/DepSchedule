package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

/**
 * PeriodPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-10T11:29:07.772005+03:00[Europe/Minsk]")

public class PeriodPutDto   {
  @JsonProperty("name")
  private String name;

  @JsonProperty("startDate")
  private LocalDate startDate;

  @JsonProperty("endDate")
  private LocalDate endDate;

  public PeriodPutDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Название периода
   * @return name
  */
  @ApiModelProperty(value = "Название периода")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PeriodPutDto startDate(LocalDate startDate) {
    this.startDate = startDate;
    return this;
  }

  /**
   * Get startDate
   * @return startDate
  */
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public PeriodPutDto endDate(LocalDate endDate) {
    this.endDate = endDate;
    return this;
  }

  /**
   * Get endDate
   * @return endDate
  */
  @ApiModelProperty(value = "")

  @Valid

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeriodPutDto periodPut = (PeriodPutDto) o;
    return Objects.equals(this.name, periodPut.name) &&
        Objects.equals(this.startDate, periodPut.startDate) &&
        Objects.equals(this.endDate, periodPut.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, startDate, endDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeriodPutDto {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
    sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
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

