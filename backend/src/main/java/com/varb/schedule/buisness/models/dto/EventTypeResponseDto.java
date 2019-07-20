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

/**
 * EventTypeResponseDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-07-19T17:00:06.885213+03:00[Europe/Minsk]")

public class EventTypeResponseDto   {
  @JsonProperty("description")
  private String description;

  @JsonProperty("color")
  private String color;

  @JsonProperty("type")
  private String type;

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


  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public EventTypeResponseDto type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} 
   * @return type
  */
  @ApiModelProperty(value = "Код события * `time1` - мобилизация * `time2` - марш * `time3` - развёртывание * {...} ")


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTypeResponseDto eventTypeResponse = (EventTypeResponseDto) o;
    return Objects.equals(this.description, eventTypeResponse.description) &&
        Objects.equals(this.color, eventTypeResponse.color) &&
        Objects.equals(this.type, eventTypeResponse.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, color, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTypeResponseDto {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

