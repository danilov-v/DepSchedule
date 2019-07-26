import { getNextDay, getDates } from "./date";

describe("utils/date.js", () => {
  describe("getNextDay", () => {
    test("should return next day", () => {
      const today = new Date(2019, 7, 7);
      const tomorrow = new Date(2019, 7, 8);

      expect(getNextDay(today).valueOf()).toEqual(tomorrow.valueOf());
    });
  });

  describe("getDates", () => {
    test("should return array of month and days between two dates", () => {
      const range = getDates(new Date(2019, 7, 7), new Date(2019, 7, 9));

      expect(range).toEqual([
        {
          name: "August",
          days: [
            new Date(2019, 7, 7),
            new Date(2019, 7, 8),
            new Date(2019, 7, 9),
          ],
        },
      ]);
    });
  });
});
