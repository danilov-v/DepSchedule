INSERT INTO PUBLIC.CALENDAR (CALENDAR_ID, NAME, SHIFT, IS_ASTRONOMICAL)
values  (1, 'First calendar', 0, FALSE),
        (2, 'Second calendar', 0, FALSE);

INSERT INTO PUBLIC.PERIOD (PERIOD_ID, NAME, START_DATE, END_DATE, CALENDAR_ID)
values  (1, 'Period 1', '2019-09-26', '2019-09-30', 1),
        (2, 'Period 2', '2019-10-03', '2019-10-05', 2);