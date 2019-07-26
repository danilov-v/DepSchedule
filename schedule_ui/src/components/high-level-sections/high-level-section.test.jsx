import React from "react";
import { shallow } from "enzyme";
import { HighLevelSection } from "./high-level-section";

describe("high-level-section.jsx", () => {
  describe("<HighLevelSection />", () => {
    test("should render and match snapshot", () => {
      const component = shallow(<HighLevelSection name={"B"} length={4} />);

      expect(component).toMatchSnapshot();
    });

    test("should render and match snapshot with isEmpty", () => {
      const component = shallow(<HighLevelSection isEmpty />);

      expect(component).toMatchSnapshot();
    });
  });
});
