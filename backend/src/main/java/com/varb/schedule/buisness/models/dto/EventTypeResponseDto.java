package com.varb.schedule.buisness.models.dto;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.varb.schedule.buisness.models.dto.EventTypePutDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;

/**
 * EventTypeResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-08-01T16:54:40.891793+03:00[Europe/Minsk]")

public class EventTypeResponseDto   {
  @JsonProperty("description")
  private String description;

  @JsonProperty("color")
  private String color;

  @JsonProperty("typeId")
  private Long typeId;

  public EventTypeResponseDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Описание события
   * @return description
  */
  @ApiModelProperty(value = "Описание события")

@Size(min=3) 
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EventTypeResponseDto color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Отображаемый цвет
   * @return color
  */
  @ApiModelProperty(value = "Отображаемый цвет")

@Size(min=3) 
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public EventTypeResponseDto typeId(Long typeId) {
    this.typeId = typeId;
    return this;
  }

  /**
   * Ссылка на тип события 
   * @return typeId
  */
  @ApiModelProperty(value = "Ссылка на тип события ")


  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTypeResponseDto eventTypeResponse = (EventTypeResponseDto) o;
    return Objects.equals(this.description, eventTypeResponse.description) &&
        Objects.equals(this.color, eventTypeResponse.color) &&
        Objects.equals(this.typeId, eventTypeResponse.typeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, color, typeId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTypeResponseDto {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    typeId: ").append(toIndentedString(typeId)).append("\n");
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

