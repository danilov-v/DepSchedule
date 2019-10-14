import React from "react";
import PropTypes from "prop-types";
import { Container, Table } from "reactstrap";
import { useFinishedEvents } from "helpers/hooks/apiEffects";
import { Title } from "components/title/title";

export function FinishedEvents() {
  const [finishedEvents] = useFinishedEvents(50);

  return (
    <Container>
      <Title text="Последнии завершённые события" />
      <div className="event-type-list">
        <Table hover striped>
          <thead>
            <tr>
              <th>Подразделение</th>
              <th>Событие</th>
              <th>Начало</th>
              <th>Конец</th>
            </tr>
          </thead>
          <tbody>
            {finishedEvents.map(event => (
              <tr key={event.eventId}>
                <td>{event.unitTitle}</td>
                <td>{event.eventTypeDescription}</td>
                <td>{event.dateFrom}</td>
                <td>{event.dateTo}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </Container>
  );
}

FinishedEvents.propTypes = {
  finishedEvents: PropTypes.arrayOf(
    PropTypes.shape({
      dateFrom: PropTypes.string,
      dateTo: PropTypes.string,
      eventId: PropTypes.number,
      eventTypeId: PropTypes.number,
      location: PropTypes.shape({
        name: PropTypes.string,
        type: PropTypes.string,
      }),
      note: PropTypes.string,
      planned: PropTypes.bool,
      unitId: PropTypes.number,
    })
  ),
};

FinishedEvents.defaultProps = {
  finishedEvents: [],
};
