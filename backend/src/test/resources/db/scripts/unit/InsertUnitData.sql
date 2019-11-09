--UnitTestRequiredData.sql has to be executed before this insert
INSERT INTO PUBLIC.CALENDAR (CALENDAR_ID, NAME, SHIFT, IS_ASTRONOMICAL, DATE_FROM, DATE_TO)
VALUES (1, 'Календарь 1', 4, false, '2019-08-18', '2019-10-05'),
       (2, 'Календарь 2', 5, true, '2019-08-19', '2019-10-06');


INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE, PLANNED, CALENDAR_ID)
values (100, null, '1.0', false, 1),
       (110, 100, '1.1', false, 1),
       (120, 100, '1.2', false, 1),
       (200, null, '2.0.0', true, 2),
       (210, 200, '2.1.0', false, 2),
       (211, 210, '2.1.1', false, 2),
       (212, 210, '2.1.2', true, 2);