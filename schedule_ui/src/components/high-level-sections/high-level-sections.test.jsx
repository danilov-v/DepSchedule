import React from "react";
import { shallow } from "enzyme";
import { addMonths } from "date-fns";
import { getDates } from "utils/date";
import { SECTIONS } from "stub-data/sections";
import { HighLevelSections } from "./high-level-sections";

const startDate = new Date();
const endDate = addMonths(startDate, 3);

const props = {
  startDate,
  range: getDates(startDate, endDate),
  sections: SECTIONS,
};

describe("high-level-sections.jsx", () => {
  describe("<HighLevelSections />", () => {
    test("should render and match snapshot", () => {
      const component = shallow(<HighLevelSections {...props} />);

      expect(component).toMatchSnapshot();
    });
  });
});
