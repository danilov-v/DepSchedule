import React from "react";
import { shallow } from "enzyme";

import { Title } from "./title";

describe("title.jsx", () => {
  describe("<Title />", () => {
    it("should render and match snapshot", () => {
      const component = shallow(
        <Title onChangeStartDate={jest.fn} onChangeEndDate={jest.fn} />
      );

      expect(component).toMatchSnapshot();
    });
  });
});
