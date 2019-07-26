import React, { Fragment } from "react";
import PropTypes from "prop-types";
import { Row } from "reactstrap";
import { cond, isEmpty, constant, stubTrue, get, isBoolean } from "lodash";
import { isWithinInterval, differenceInDays, isAfter } from "date-fns";
import { HighLevelSection } from "./high-level-section";

const getDifferenceInDays = ({ day, currentSection, startDate }) =>
  differenceInDays(
    day,
    isAfter(startDate, currentSection.startDate)
      ? startDate
      : currentSection.startDate
  );

export function HighLevelSections({ startDate, range, sections }) {
  const renderingSection = {};

  return (
    <Row className="high-level-sections flex-nowrap" noGutters>
      {range.map(month => (
        <Fragment key={month.name}>
          {month.days.map(day => {
            const dayWithoutMinutes = new Date(
              day.getFullYear(),
              day.getMonth(),
              day.getDate()
            );
            const currentSection = sections.find(section =>
              isWithinInterval(dayWithoutMinutes, {
                start: section.startDate,
                end: section.endDate,
              })
            );

            const renderSection = cond([
              [
                constant(isEmpty(currentSection)),
                constant(<HighLevelSection isEmpty />),
              ],
              [
                constant(
                  !isBoolean(renderingSection[get(currentSection, "name")])
                ),
                () => {
                  renderingSection[currentSection.name] = true;

                  return (
                    <HighLevelSection
                      name={currentSection.name}
                      length={
                        getDifferenceInDays({
                          day: dayWithoutMinutes,
                          currentSection,
                          startDate,
                        }) + 1
                      }
                    />
                  );
                },
              ],
              [stubTrue, constant(null)],
            ]);

            return <Fragment key={day}>{renderSection()}</Fragment>;
          })}
        </Fragment>
      ))}
    </Row>
  );
}

HighLevelSections.propTypes = {
  range: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      days: PropTypes.arrayOf(PropTypes.object),
    })
  ),
  sections: PropTypes.arrayOf(
    PropTypes.shape({
      name: PropTypes.string,
      startDate: PropTypes.object,
      endDate: PropTypes.object,
    })
  ),
};

HighLevelSections.defaultProps = {
  sections: [],
  range: [],
};
