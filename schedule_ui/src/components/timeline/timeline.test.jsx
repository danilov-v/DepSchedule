import React from "react";
import { shallow } from "enzyme";

import { Timeline } from "./timeline";

describe("timeline.jsx", () => {
  describe("<Timeline />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<Timeline />);

      expect(component).toMatchSnapshot();
    });
  });
});
