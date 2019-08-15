package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * UnitPutDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-14T11:33:21.219802+03:00[Europe/Minsk]")

public class UnitPutDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("parentId")
  private Long parentId;

  @JsonProperty("unitLevel")
  private Integer unitLevel;

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

  public UnitPutDto unitLevel(Integer unitLevel) {
    this.unitLevel = unitLevel;
    return this;
  }

  /**
   * Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение 
   * @return unitLevel
  */
  @ApiModelProperty(value = "Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение ")


  public Integer getUnitLevel() {
    return unitLevel;
  }

  public void setUnitLevel(Integer unitLevel) {
    this.unitLevel = unitLevel;
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
        Objects.equals(this.parentId, unitPut.parentId) &&
        Objects.equals(this.unitLevel, unitPut.unitLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, parentId, unitLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitPutDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    unitLevel: ").append(toIndentedString(unitLevel)).append("\n");
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

