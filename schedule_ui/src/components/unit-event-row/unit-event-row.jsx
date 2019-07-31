import React from "react";
import PropTypes from "prop-types";
import { Row } from "reactstrap";
import { EventCell } from "./event-cell";
import { getAllDatesFromRange } from "../../utils/date";

export function UnitEventRow({ range, unitGroup }) {
  return unitGroup.map(unit => (
    <Row key={unit.unitId} noGutters>
      {getAllDatesFromRange(range).map(date => (
        <EventCell
          key={date.getTime()}
          eventDate={date}
          eventOwnerId={unit.unitId}
          onClick={console.log}
        />
      ))}
    </Row>
  ));
}

UnitEventRow.propTypes = {
  range: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      days: PropTypes.arrayOf(PropTypes.object),
    })
  ),
  unitGroup: PropTypes.arrayOf(PropTypes.object),
};

UnitEventRow.defaultProps = {
  range: [],
  unitGroup: [],
};
