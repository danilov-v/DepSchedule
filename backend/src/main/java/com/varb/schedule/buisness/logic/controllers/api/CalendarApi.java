/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.0.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.varb.schedule.buisness.logic.controllers.api;

import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.dto.CalendarResponseDto;
import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-10-29T10:47:52.582273+03:00[Europe/Minsk]")

@Validated
@Api(value = "calendar", description = "the calendar API")
public interface CalendarApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @ApiOperation(value = "Удалить информацию о календаре", nickname = "calendarDelete", notes = "", authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "calendar", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorMessageDto.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessageDto.class) })
    @RequestMapping(value = "/calendar/{calendarId}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    default ResponseEntity<Void> calendarDelete(@ApiParam(value = "",required=true) @PathVariable("calendarId") Long calendarId) {
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @ApiOperation(value = "Получение информации о календарях", nickname = "calendarGet", notes = "", response = CalendarResponseDto.class, responseContainer = "List", authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "calendar", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = CalendarResponseDto.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorMessageDto.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessageDto.class) })
    @RequestMapping(value = "/calendar",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<CalendarResponseDto>> calendarGet() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "null");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.valueOf(200));

    }


    @ApiOperation(value = "Получение информации о конкретном календаре", nickname = "calendarGetById", notes = "", response = CalendarResponseDto.class, authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "calendar", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = CalendarResponseDto.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorMessageDto.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessageDto.class) })
    @RequestMapping(value = "/calendar/{calendarId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<CalendarResponseDto> calendarGetById(@ApiParam(value = "",required=true) @PathVariable("calendarId") Long calendarId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "null");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.valueOf(200));

    }


    @ApiOperation(value = "Создать календарь (Календарь автоматически становится активным)", nickname = "calendarPost", notes = "", response = CalendarResponseDto.class, authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "calendar", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = CalendarResponseDto.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorMessageDto.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessageDto.class) })
    @RequestMapping(value = "/calendar",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default ResponseEntity<CalendarResponseDto> calendarPost(@ApiParam(value = ""  )  @Valid @RequestBody CalendarBaseReqDto calendarBaseReqDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "null");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.valueOf(200));

    }


    @ApiOperation(value = "Редактировать информацию в конкретном календаре (Календарь автоматически становится активным)", nickname = "calendarPut", notes = "", response = CalendarResponseDto.class, authorizations = {
        @Authorization(value = "JWT")
    }, tags={ "calendar", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = CalendarResponseDto.class),
        @ApiResponse(code = 400, message = "Bad request", response = ErrorMessageDto.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorMessageDto.class) })
    @RequestMapping(value = "/calendar/{calendarId}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    default ResponseEntity<CalendarResponseDto> calendarPut(@ApiParam(value = "",required=true) @PathVariable("calendarId") Long calendarId,@ApiParam(value = ""  )  @Valid @RequestBody CalendarBaseDto calendarBaseDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "null");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.valueOf(200));

    }

}
