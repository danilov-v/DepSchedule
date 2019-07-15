import React from "react";
import { shallow } from "enzyme";

import { Home } from "./home";

describe("home.jsx", () => {
  describe("<Home />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<Home />);

      expect(component).toMatchSnapshot();
    });
  });
});
