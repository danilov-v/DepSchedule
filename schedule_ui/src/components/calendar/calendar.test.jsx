import React from "react";
import { shallow } from "enzyme";
import { Calendar } from "./calendar";

const data = [{ name: "November", days: [new Date(2019, 7, 7)] }];

describe("calendar.jsx", () => {
  describe("<Calendar />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(<Calendar range={data} />);

      expect(component).toMatchSnapshot();
    });
  });
});
