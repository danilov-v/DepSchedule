import React from "react";
import { shallow } from "enzyme";
import { EventCell } from "./event-cell";

describe("event-cell-row.jsx", () => {
  describe("<EventCell />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<EventCell />);

      expect(component).toMatchSnapshot();
    });

    it("should call onClick prop with eventOwnerId and eventDate as arguments", () => {
      const date = new Date("2019-12-4");
      const id = 1;

      const mockFunction = jest.fn();
      const component = shallow(
        <EventCell eventOwnerId={id} eventDate={date} onClick={mockFunction} />
      );

      component.find("div").simulate("click");
      expect(mockFunction).toHaveBeenCalledWith(id, date);
    });
  });
});
