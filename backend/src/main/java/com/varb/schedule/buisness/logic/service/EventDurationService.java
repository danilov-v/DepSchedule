package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventDurationRepository;
import com.varb.schedule.buisness.models.dto.EventDurationDto;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventDurationService extends AbstractService<EventDuration, EventDurationPK> {
    private final EventDurationRepository eventDurationRepository;
    private final ModelMapperCustomize modelMapper;
    private final UnitService unitService;
    private final EventTypeService eventTypeService;

    /**
     * Изменяем длительность по умолчанию для событий в подразделении {@code unitId} и с типом {@code eventTypeId}
     *
     * @param unitId      Id подразделения
     * @param eventTypeId Id типа события
     */
    public EventDuration merge(Long unitId, Long eventTypeId, EventDurationDto eventDurationPutDto) {
        checkConsistency(unitId, eventTypeId);
        Optional<EventDuration> optionalEventDuration = findByIdOptional(new EventDurationPK(unitId, eventTypeId));

        EventDuration eventDuration;
        if (optionalEventDuration.isPresent()) {
            eventDuration = optionalEventDuration.get();
            modelMapper.map(eventDurationPutDto, eventDuration);
        } else {
            eventDuration = modelMapper.map(eventDurationPutDto, EventDuration.class);
            eventDurationRepository.save(eventDuration.setCompositePK(new EventDurationPK(unitId, eventTypeId)));
        }

        return eventDuration;
    }

    public void delete(Long unitId, Long eventTypeId) {
        super.delete(new EventDurationPK(unitId, eventTypeId));
    }


    private void checkConsistency(Long unitId, Long eventTypeId) {
        unitService.checkExists(unitId);
        eventTypeService.checkExists(eventTypeId);
    }

    public List<EventDuration> getAll(@Nullable Long unitId) {
        if (unitId == null)
            return super.findAll();
        else
            return getByUnitId(unitId);
    }

    public List<EventDuration> getByUnitId(Long unitId) {
        return eventDurationRepository.findByUnitId(unitId);
    }

    public EventDuration findById(Long unitId, Long eventTypeId) {
        return super.findById(new EventDurationPK(unitId, eventTypeId));
    }

    /**
     * Update 'duration' in {@link com.varb.schedule.buisness.models.entity.EventDuration}
     */
    void updateEventDuration(Event event) {
        int duration = (int) ChronoUnit.DAYS.between(event.getDateFrom(), event.getDateTo());
        merge(event.getUnitId(), event.getEventTypeId(), new EventDurationDto().duration(duration));
    }

    @Override
    protected String notFindMessage(EventDurationPK eventDurationPK) {
        return "Не найден EventDuration" +
                "(unitId=" + eventDurationPK.getUnitId() + ", eventTypeId=" + eventDurationPK.getEventTypeId() + ")";
    }
}
