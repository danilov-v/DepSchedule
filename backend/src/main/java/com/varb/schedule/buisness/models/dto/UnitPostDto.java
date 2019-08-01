package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * UnitPostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-01T11:31:39.739946+03:00[Europe/Minsk]")

public class UnitPostDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("parentId")
  private Long parentId;

  @JsonProperty("unitLevel")
  private Integer unitLevel;

  public UnitPostDto title(String title) {
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

  public UnitPostDto parentId(Long parentId) {
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

  public UnitPostDto unitLevel(Integer unitLevel) {
    this.unitLevel = unitLevel;
    return this;
  }

  /**
   * Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение 
   * @return unitLevel
  */
  @ApiModelProperty(required = true, value = "Уровень подразделения * `1` - Система управления * `2` - Орган управления * `3` - Пункт управления * `4` - Подразделение ")
  @NotNull


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
    UnitPostDto unitPost = (UnitPostDto) o;
    return Objects.equals(this.title, unitPost.title) &&
        Objects.equals(this.parentId, unitPost.parentId) &&
        Objects.equals(this.unitLevel, unitPost.unitLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, parentId, unitLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitPostDto {\n");
    
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

