import React, { Fragment, useEffect } from "react";
import PropTypes from "prop-types";
import { useDispatch, useSelector } from "react-redux";
import { Row } from "reactstrap";
import { cond, isEmpty, constant, stubTrue, get, isBoolean } from "lodash";
import { isWithinInterval, differenceInDays, isAfter } from "date-fns";
import { getPeriodsSelector } from "redux/selectors/periods";
import { getUserDataSelector } from "redux/selectors/user";
import { fetchPeriods } from "redux/actions/periods";
import { getDayWithoutMinutes } from "utils/date";
import { HighLevelSection } from "./high-level-section";

const getDifferenceInDays = ({ day, currentSection, startDate }) =>
  differenceInDays(
    day,
    isAfter(startDate, currentSection.startDate)
      ? startDate
      : currentSection.startDate
  );

export const formatPeriods = periods =>
  periods.map(period => ({
    ...period,
    startDate: getDayWithoutMinutes(new Date(period.startDate)),
    endDate: getDayWithoutMinutes(new Date(period.endDate)),
  }));

export function HighLevelSections({ range }) {
  const dispatch = useDispatch();
  const periods = formatPeriods(useSelector(getPeriodsSelector));
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
  sections: [],
  range: [],
};
