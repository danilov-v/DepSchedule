export const getNextDay = date => {
  const resultDate = new Date(
    date.getFullYear(),
    date.getMonth(),
    date.getDate()
  );
  resultDate.setDate(date.getDate() + 1);
  return resultDate;
};

export const getDates = (startDate, endDate) => {
  const results = [];
  const checkMonth = month => result => result.name === month;
  let currentDate = startDate;

  while (currentDate <= endDate) {
    const month = currentDate.toLocaleString("default", { month: "long" });
    const checkMonthFn = checkMonth(month);
    if (!results.some(checkMonthFn)) {
      results.push({ name: month, days: [] });
    }
    const currentMonth = results.find(checkMonthFn);
    currentMonth.days.push(currentDate);

    currentDate = getNextDay(currentDate);
  }

  // Improve this
  const formattedData = [...results].reverse();
  formattedData.map(month => month.days.reverse());

  return formattedData;
};

export const getAllDatesFromRange = range =>
  range.reduce((days, month) => [...days, month.days], []).flat();
