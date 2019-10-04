import React from "react";
import { shallow } from "enzyme";
import { CalendarMonthCellText } from "./calendar-month-cell-text";

describe("calendar-month-cell-text", () => {
  describe("<CalendarMonthCellText />", () => {
    test("should render and match snapshot", () => {
      const component = shallow(
        <CalendarMonthCellText text="test" length={5} />
      );

      expect(component).toMatchSnapshot();
    });
  });
});
