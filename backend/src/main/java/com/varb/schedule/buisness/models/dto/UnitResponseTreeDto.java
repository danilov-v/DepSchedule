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
public class UnitResponseTreeDto extends UnitPutDto  {
  @JsonProperty("unitId")
  private Long unitId = null;

  @JsonProperty("childUnit")
  @Valid
  private List<UnitResponseTreeDto> childUnit = null;

  public UnitResponseTreeDto unitId(Long unitId) {
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

  public UnitResponseTreeDto childUnit(List<UnitResponseTreeDto> childUnit) {
    this.childUnit = childUnit;
    return this;
  }

  public UnitResponseTreeDto addChildUnitItem(UnitResponseTreeDto childUnitItem) {
    if (this.childUnit == null) {
      this.childUnit = new ArrayList<UnitResponseTreeDto>();
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
  public List<UnitResponseTreeDto> getChildUnit() {
    return childUnit;
  }

  public void setChildUnit(List<UnitResponseTreeDto> childUnit) {
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
    UnitResponseTreeDto unitResponseTreeDto = (UnitResponseTreeDto) o;
    return Objects.equals(this.unitId, unitResponseTreeDto.unitId) &&
        Objects.equals(this.childUnit, unitResponseTreeDto.childUnit) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(unitId, childUnit, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UnitResponseTreeDto {\n");
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
