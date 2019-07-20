import React from "react";
import { shallow } from "enzyme";
import { CalendarCell } from "./calendar-cell";

describe("calendar-cell.jsx", () => {
  describe("<CalendarCell />", () => {
    test("should render and match snapshot", () => {
      const component = shallow(<CalendarCell text="25" />);

      expect(component).toMatchSnapshot();
    });

    test("should render and match snapshot with fluid", () => {
      const component = shallow(<CalendarCell text="25" fluid />);

      expect(component).toMatchSnapshot();
    });
  });
});
