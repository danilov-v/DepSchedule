package com.varb.schedule.buisness.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * EventTypePostDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-09-06T09:40:00.421712+03:00[Europe/Minsk]")

public class EventTypePostDto   {
  @JsonProperty("description")
  private String description;

  @JsonProperty("color")
  private String color;

  public EventTypePostDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Описание события
   * @return description
  */
  @ApiModelProperty(required = true, value = "Описание события")
  @NotNull

@Size(min=3) 
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public EventTypePostDto color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Отображаемый цвет
   * @return color
  */
  @ApiModelProperty(required = true, value = "Отображаемый цвет")
  @NotNull

@Size(min=3) 
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventTypePostDto eventTypePost = (EventTypePostDto) o;
    return Objects.equals(this.description, eventTypePost.description) &&
        Objects.equals(this.color, eventTypePost.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, color);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventTypePostDto {\n");
    
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
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

