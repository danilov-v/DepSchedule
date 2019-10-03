import React from "react";
import shallow from "enzyme/shallow";
import { PeriodsPopup, validatePeriodForm } from "./periods-popup";

const props = {
  toggle: () => {},
  onPeriodSubmit: () => {},
  isOpen: true,
  defaultFormData: {
    name: "test",
    startDate: null,
    endDate: null,
  },
};

describe("periods-popup.jsx", () => {
  describe("<PeriodsPopup />", () => {
    test("should render and match snapshot with create type", () => {
      const component = shallow(<PeriodsPopup {...props} type="create" />);

      expect(component).toMatchSnapshot();
    });

    test("should render and match snapshot with edit type", () => {
      const component = shallow(<PeriodsPopup {...props} type="edit" />);

      expect(component).toMatchSnapshot();
    });

    test("should return map of all validation errors", () => {
      expect(
        validatePeriodForm({
          startDate: null,
          endDate: null,
          name: "",
        })
      ).toEqual({
        dates: "Необходимо выбрать дату периода",
        startDate: "Необходимо выбрать дату начала периода",
        endDate: "Необходимо выбрать дату конца периода",
        name: "Назвние должно содержать минимум 1 символ",
      });
    });

    test("should return name error", () => {
      expect(
        validatePeriodForm({
          startDate: new Date(),
          endDate: new Date(),
          name: "   ",
        })
      ).toEqual({
        name: "Назвние должно содержать минимум 1 символ",
      });
    });

    test("should return emty error object", () => {
      expect(
        validatePeriodForm({
          startDate: new Date(),
          endDate: new Date(),
          name: "asd",
        })
      ).toEqual({});
    });
  });
});
