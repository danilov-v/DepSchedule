import React from "react";
import PropTypes from "prop-types";
import { Container, Table } from "reactstrap";
import { Title } from "components/title/title";

export function FinishedEvents({ finishedEvents }) {
  const renderRow = (event, index) => (
    <tr key={event.eventId}>
      <th scope="row">{index}</th>
      <td>{event.unitId}</td>
      <td>{event.name}</td>
      <td>{event.startDate}</td>
      <td>{event.endDate}</td>
    </tr>
  );

  return (
    <Container>
      <Title text="Периоды" />
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
