import React, { Fragment, useEffect } from "react";
import PropTypes from "prop-types";
import { useDispatch, useSelector } from "react-redux";
import { Row } from "reactstrap";
import { cond, isEmpty, constant, stubTrue, get, isBoolean } from "lodash";
import { isWithinInterval, differenceInDays, isAfter } from "date-fns";
import { getFormattedPeriodsSelector } from "redux/selectors/periods";
import { getUserDataSelector } from "redux/selectors/ui";
import { fetchPeriods } from "redux/actions/periods";
import { HighLevelSection } from "./high-level-section";

const getDifferenceInDays = ({ day, currentSection, startDate }) =>
  differenceInDays(
    day,
    isAfter(startDate, currentSection.startDate)
      ? startDate
      : currentSection.startDate
  );

export function HighLevelSections({ range }) {
  const dispatch = useDispatch();
  const periods = useSelector(getFormattedPeriodsSelector);
  const { startDate } = useSelector(getUserDataSelector);
  const renderingSection = {};

  useEffect(() => {
    dispatch(fetchPeriods());
  }, [dispatch]);

  if (!periods.length) return null;

  return (
    <Row className="high-level-sections flex-nowrap stick-to-top" noGutters>
      {range.map(month => (
        <Fragment key={month.name}>
          {month.days.map(day => {
            const currentSection = periods.find(section =>
              isWithinInterval(day, {
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
                          day,
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
};

HighLevelSections.defaultProps = {
  range: [],
};
