import React from "react";
import render from "enzyme/render";
import { AuthServiceProvider } from "components/auth-service/auth-service";
import { PeriodsList } from "./periods-list";

const props = {
  onPeriodEdit: () => {},
  onPeriodRemove: () => {},
  periods: [
    {
      periodId: 0,
      name: "test",
      startDate: "2019-01-01",
      endDate: "2020-01-01",
    },
  ],
};

describe("periods-list.jsx", () => {
  describe("<PeriodsList />", () => {
    test("should render and match snapshot", () => {
      const component = render(
        <AuthServiceProvider>
          <PeriodsList {...props} />
        </AuthServiceProvider>
      );

      expect(component).toMatchSnapshot();
    });
  });
});
