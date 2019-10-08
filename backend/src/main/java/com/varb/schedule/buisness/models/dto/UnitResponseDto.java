package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * UnitResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-08T17:10:57.427864+03:00[Europe/Minsk]")

public class UnitResponseDto   {
  @JsonProperty("title")
  private String title;

  @JsonProperty("flag")
  private String flag;

  @JsonProperty("exists")
  private Boolean exists;

  @JsonProperty("parentId")
  private Long parentId;

  @JsonProperty("unitId")
  private Long unitId;

  public UnitResponseDto title(String title) {
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

  public UnitResponseDto flag(String flag) {
    this.flag = flag;
    return this;
  }

  /**
   * Путь к изображению флага
   * @return flag
  */
  @ApiModelProperty(required = true, value = "Путь к изображению флага")
  @NotNull


  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public UnitResponseDto exists(Boolean exists) {
    this.exists = exists;
    return this;
  }

  /**
   * Планируемое подразделение? (true/false)
   * @return exists
  */
  @ApiModelProperty(required = true, value = "Планируемое подразделение? (true/false)")
  @NotNull


  public Boolean getExists() {
    return exists;
  }

  public void setExists(Boolean exists) {
    this.exists = exists;
  }

  public UnitResponseDto parentId(Long parentId) {
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

  public UnitResponseDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Get unitId
   * @return unitId
  */
  @ApiModelProperty(value = "")


  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnitResponseDto unitResponse = (UnitResponseDto) o;
    return Objects.equals(this.title, unitResponse.title) &&
        Objects.equals(this.flag, unitResponse.flag) &&
        Objects.equals(this.exists, unitResponse.exists) &&
        Objects.equals(this.parentId, unitResponse.parentId) &&
        Objects.equals(this.unitId, unitResponse.unitId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, flag, exists, parentId, unitId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseDto {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    flag: ").append(toIndentedString(flag)).append("\n");
    sb.append("    exists: ").append(toIndentedString(exists)).append("\n");
    sb.append("    parentId: ").append(toIndentedString(parentId)).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
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

