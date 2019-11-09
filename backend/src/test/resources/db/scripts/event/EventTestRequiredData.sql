INSERT INTO PUBLIC.CALENDAR (CALENDAR_ID, NAME, SHIFT, IS_ASTRONOMICAL, DATE_FROM, DATE_TO)
VALUES (1, 'Календарь 1', 4, false, '2019-08-18', '2019-10-05'),
       (2, 'Календарь 2', 5, true, '2019-08-19', '2019-10-06');

--insert EVENT_TYPE data
INSERT INTO PUBLIC.EVENT_TYPE (TYPE_ID, COLOR, DESCRIPTION)
values  (1, 'red', 'mobilization'),
        (2, 'green', 'deployment');

--insert UNIT data
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE, FLAG, PLANNED, CALENDAR_ID)
values  (100, null, 'parent1', 'flag1', false, 1),
        (200, 100, 'child1', 'flag2', false, 1),
        (300, 100, 'child2', 'flag3', false, 1),
        (400, null, 'parent2', 'flag4', false, 2);

--insert EVENT_DURATION data
INSERT INTO PUBLIC.EVENT_DURATION (EVENT_TYPE_ID, DURATION, UNIT_ID)
values  (1, 5, 200),
        (2, 6, 300);