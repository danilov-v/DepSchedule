package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Узел дерева. Содержит информацию о подразделении
 */
@ApiModel(description = "Узел дерева. Содержит информацию о подразделении")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-07-26T17:36:21.790726+03:00[Europe/Minsk]")
public class UnitResponseThreeDto extends UnitPutDto  {
  @JsonProperty("unitId")
  private Long unitId = null;

  @JsonProperty("childUnit")
  @Valid
  private List<UnitResponseThreeDto> childUnit = null;

  public UnitResponseThreeDto unitId(Long unitId) {
    this.unitId = unitId;
    return this;
  }

  /**
   * Get unitId
   * @return unitId
  **/
  @ApiModelProperty(value = "")

  public Long getUnitId() {
    return unitId;
  }

  public void setUnitId(Long unitId) {
    this.unitId = unitId;
  }

  public UnitResponseThreeDto childUnit(List<UnitResponseThreeDto> childUnit) {
    this.childUnit = childUnit;
    return this;
  }

  public UnitResponseThreeDto addChildUnitItem(UnitResponseThreeDto childUnitItem) {
    if (this.childUnit == null) {
      this.childUnit = new ArrayList<UnitResponseThreeDto>();
    }
    this.childUnit.add(childUnitItem);
    return this;
  }

  /**
   * Get childUnit
   * @return childUnit
  **/
  @ApiModelProperty(value = "")
  @Valid
  public List<UnitResponseThreeDto> getChildUnit() {
    return childUnit;
  }

  public void setChildUnit(List<UnitResponseThreeDto> childUnit) {
    this.childUnit = childUnit;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnitResponseThreeDto unitResponseThreeDto = (UnitResponseThreeDto) o;
    return Objects.equals(this.unitId, unitResponseThreeDto.unitId) &&
        Objects.equals(this.childUnit, unitResponseThreeDto.childUnit) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, childUnit, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseThreeDto {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    unitId: ").append(toIndentedString(unitId)).append("\n");
    sb.append("    childUnit: ").append(toIndentedString(childUnit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
