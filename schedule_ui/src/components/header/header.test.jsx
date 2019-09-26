import React from "react";
import { shallow } from "enzyme";
import { set } from "lodash";
import { HeaderUI } from "./header";

const mockDate = new Date("2019-05-05");
const props = {
  startDate: mockDate,
  endDate: mockDate,
  operationalDate: mockDate,
  onChangeStartDate: () => {},
  onChangeEndDate: () => {},
  history: {
    location: {
      pathname: "/",
    },
  },
};

describe("header.jsx", () => {
  describe("<HeaderUI />", () => {
    test("should render and match snapshot for home page", () => {
      const wrapper = shallow(<HeaderUI {...props} />);

      expect(wrapper).toMatchSnapshot();
    });

    test("should render and match snapshot for NOT home page", () => {
      set(props, "history.location.pathname", "/test");

      const wrapper = shallow(<HeaderUI {...props} />);

      expect(wrapper).toMatchSnapshot();
    });
  });
});
