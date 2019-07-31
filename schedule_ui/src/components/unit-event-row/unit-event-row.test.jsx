import React from "react";
import { shallow } from "enzyme";
import { UnitEventRow } from "./unit-event-row";

const range = [{ name: "November", days: [new Date(2019, 7, 7)] }];
const unitGroup = [
  {
    title: "Система управления 1.1.1",
    parentId: 201,
    unitLevel: 3,
    unitId: 251,
  },
  {
    title: "Система управления 1.1.2",
    parentId: 201,
    unitLevel: 3,
    unitId: 252,
  },
];

describe("unit-event-row.jsx", () => {
  describe("<UnitEventRow />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(
        <UnitEventRow range={range} unitGroup={unitGroup} />
      );

      expect(component).toMatchSnapshot();
    });
  });
});
