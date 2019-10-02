import React from "react";
import shallow from "enzyme/shallow";
import { Notification } from "./notification";

const props = {
  data: {
    isOpen: true,
    title: "test",
    body: "some text",
  },
};

describe("notification.jsx", () => {
  describe("<Notification />", () => {
    test("should render and match snapshot", () => {
      const component = shallow(<Notification {...props} />);

      expect(component).toMatchSnapshot();
    });
  });
});
