import React from "react";
import { shallow } from "enzyme";
import { Calendar } from "./calendar";

const data = [{ name: "November", days: [new Date(2019, 7, 7)] }];
const unitsGroups = [
  [
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
  ],

  [
    {
      title: "Система управления 2.1.1",
      parentId: 301,
      unitLevel: 3,
      unitId: 2522,
    },
    {
      title: "Система управления 2.1.2",
      parentId: 301,
      unitLevel: 3,
      unitId: 2533,
    },
  ],
];

describe("calendar.jsx", () => {
  describe("<Calendar />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(
        <Calendar
          range={data}
          operationalRange={data}
          unitGroups={unitsGroups}
        />
      );

      expect(component).toMatchSnapshot();
    });
  });
});
