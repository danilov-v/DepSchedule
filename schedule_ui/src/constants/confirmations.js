export const EVENT_TYPE_CONFIRMATION_OPTIONS = {
  title: "Вы уверены что хотите удалить тип события ?",
  body: "При удалении все события этого типа также будут удалены",
  catchOnCancel: false,
};

export const getUnitRemoveConfirmationOptions = ({ title }) => ({
  title: "Удаление поздразделения",
  body: `Вы уверены что хотите удалить ${title} подразделение?`,
  catchOnCancel: false,
});
