import { getNextDay, getDates, getAllDatesFromRange } from "./date";

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
            new Date(2019, 7, 9),
            new Date(2019, 7, 8),
            new Date(2019, 7, 7),
          ],
        },
      ]);
    });
  });

  describe("getAllDatesFromRange", () => {
    test("should return array of dates from array of months and dates", () => {
      const range = [
        {
          name: "August",
          days: [
            new Date(2019, 7, 7),
            new Date(2019, 7, 8),
            new Date(2019, 7, 9),
          ],
        },
        {
          name: "September",
          days: [
            new Date(2019, 8, 7),
            new Date(2019, 8, 8),
            new Date(2019, 8, 9),
          ],
        },
      ];

      const dates = [
        new Date(2019, 7, 7),
        new Date(2019, 7, 8),
        new Date(2019, 7, 9),
        new Date(2019, 8, 7),
        new Date(2019, 8, 8),
        new Date(2019, 8, 9),
      ];

      expect(getAllDatesFromRange(range)).toEqual(dates);
    });
  });
});
