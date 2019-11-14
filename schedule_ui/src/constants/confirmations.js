export const EVENT_TYPE_CONFIRMATION_OPTIONS = {
  title: "Вы уверены что хотите удалить тип события ?",
  body: "При удалении все события этого типа также будут удалены",
  catchOnCancel: false,
};

export const PERIOD_CONFIRMATION_OPTIONS = {
  title: "Удаление секции",
  body: "Вы уверены что хотите удалить секцию?",
  catchOnCancel: false,
};

export const CALENDAR_CONFIRMATION_OPTIONS = {
  title: "Удаление Календаря",
  body: "Вы уверены что хотите удалить календарь?",
  catchOnCancel: false,
};

export const getUnitRemoveConfirmationOptions = ({ title }) => ({
  title: "Удаление поздразделения",
  body: `Вы уверены что хотите удалить ${title} подразделение?`,
  catchOnCancel: false,
});

export const EVENT_CONFIRMATION_OPTIONS = {
  title: "Удаление События",
  body: "Вы уверены что хотите удалить событие?",
  catchOnCancel: false,
};
