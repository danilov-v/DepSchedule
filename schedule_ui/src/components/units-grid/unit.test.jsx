import React from "react";
import { shallow } from "enzyme";

import { Unit } from "./unit";

describe("units-grid.jsx", () => {
  describe("<Unit />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<Unit title="title" />);

      expect(component).toMatchSnapshot();
    });

    test("should render and match snapshot with lastGen", () => {
      const component = shallow(<Unit title="title" lastGen />);

      expect(component).toMatchSnapshot();
    });
  });
});
