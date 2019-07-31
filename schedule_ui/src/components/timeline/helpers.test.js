import { getLastGenUnits } from "./helpers";

const units = [
  {
    unitId: 1,
    childUnit: [],
  },
  {
    unitId: 2,
    childUnit: [
      {
        unitId: 21,
        childUnit: [],
      },
      {
        unitId: 22,
        childUnit: [],
      },
    ],
  },
  {
    unitId: 3,
    childUnit: [
      {
        unitId: 31,
        childUnit: [
          {
            unitId: 311,
            childUnit: [],
          },
        ],
      },
      {
        unitId: 32,
        childUnit: [],
      },
    ],
  },
];

describe("timeline/helpers.js", () => {
  describe("getLastGenUnits", () => {
    test("should return array of units without a childs grouped to array by common root", () => {
      const lastGenUnits = getLastGenUnits(units);
      expect(lastGenUnits).toEqual([
        [
          {
            unitId: 1,
            childUnit: [],
          },
        ],
        [
          {
            unitId: 21,
            childUnit: [],
          },
          {
            unitId: 22,
            childUnit: [],
          },
        ],
        [
          {
            unitId: 311,
            childUnit: [],
          },
          {
            unitId: 32,
            childUnit: [],
          },
        ],
      ]);
    });
  });
});
