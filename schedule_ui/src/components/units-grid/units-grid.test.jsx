import React from "react";
import { shallow } from "enzyme";
import { UNITS } from "stub-data/units";

import { UnitsGrid } from "./units-grid";

describe("units-grid.jsx", () => {
  const testData = UNITS[1];

  describe("<UnitsGrid />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<UnitsGrid units={[testData]} />);

      expect(component).toMatchSnapshot();
    });
  });
});
