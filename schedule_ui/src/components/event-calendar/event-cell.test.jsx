import React from "react";
import { shallow } from "enzyme";
import { EventCell } from "./event-cell";

describe("event-cell-row.jsx", () => {
  describe("<EventCell />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<EventCell />);

      expect(component).toMatchSnapshot();
    });

    it("should call onClick prop", () => {
      const mockFunction = jest.fn();
      const component = shallow(<EventCell onClick={mockFunction} />);

      component.find("div").simulate("click");
      expect(mockFunction).toHaveBeenCalled();
    });
  });
});
