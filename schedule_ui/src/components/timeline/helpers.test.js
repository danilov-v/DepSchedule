import { getLastGenUnits } from "./helpers";
import { UNITS } from "../../stub-data/units";

describe("timeline/helpers.js", () => {
  describe("getLastGenUnits", () => {
    test("should array of groups of units without a childs", () => {
      const lastGenUnits = getLastGenUnits(UNITS);
      expect(lastGenUnits).toEqual([
        [
          {
            title: "Система управления 1.1.1",
            parentId: 201,
            unitLevel: 3,
            unitId: 251,
          },
        ],
        [
          {
            title: "Подразделение 2.1.1.1",
            parentId: 4,
            unitLevel: 4,
            unitId: 5,
          },
          {
            title: "Подразделение 2.1.1.2",
            parentId: 4,
            unitLevel: 4,
            unitId: 51,
          },
          {
            title: "Орган управления 2.2",
            parentId: 2,
            unitLevel: 2,
            unitId: 33,
            childUnit: null,
          },
        ],
      ]);
    });
  });
});
