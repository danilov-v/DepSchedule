package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.UnitBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * UnitPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-24T12:01:31.603414+03:00[Europe/Minsk]")

public class UnitPutDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("flag")
  private String flag;

  @JsonProperty("planned")
  private Boolean planned = false;

  @JsonProperty("parentId")
  private Long parentId;

  public UnitPutDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Название подразделения
   * @return title
  */
  @ApiModelProperty(value = "Название подразделения")

@Size(min=2) 
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public UnitPutDto flag(String flag) {
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

  public UnitPutDto planned(Boolean planned) {
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

  public UnitPutDto parentId(Long parentId) {
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
    UnitPutDto unitPut = (UnitPutDto) o;
    return Objects.equals(this.title, unitPut.title) &&
        Objects.equals(this.flag, unitPut.flag) &&
        Objects.equals(this.planned, unitPut.planned) &&
        Objects.equals(this.parentId, unitPut.parentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, flag, planned, parentId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitPutDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    planned: ").append(toIndentedString(planned)).append("\n");
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

