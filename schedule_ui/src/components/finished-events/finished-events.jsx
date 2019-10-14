import React from "react";
import PropTypes from "prop-types";
import { Container, Table } from "reactstrap";
import { useFinishedEvents } from "helpers/hooks/apiEffects";
import { Title } from "components/title/title";

export function FinishedEvents() {
  const [finishedEvents] = useFinishedEvents(50);
  console.log(finishedEvents);
  const renderRow = (event, index) => (
    <tr key={event.eventId}>
      <th scope="row">{index}</th>
      <td>{event.unitTitle}</td>
      <td>{event.eventTypeDescription}</td>
      <td>{event.dateFrom}</td>
      <td>{event.dateTo}</td>
    </tr>
  );

  return (
    <Container>
      <Title text="Последнии завершённые события" />
      <div className="event-type-list">
        <Table hover>
          <thead>
            <tr>
              <th>#</th>
              <th>Подразделение</th>
              <th>Событие</th>
              <th>Начало</th>
              <th>Конец</th>
            </tr>
          </thead>
          <tbody>{finishedEvents.map(renderRow)}</tbody>
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
