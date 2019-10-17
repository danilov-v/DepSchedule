INSERT INTO PUBLIC.CALENDAR (CALENDAR_ID, NAME, SHIFT, IS_ASTRONOMICAL)
VALUES (1, 'Календарь 1', 4, false),
       (2, 'Календарь 2', 5, true);

--insert EVENT_TYPE data
INSERT INTO PUBLIC.EVENT_TYPE (TYPE_ID, COLOR, DESCRIPTION)
values  (1, 'red', 'mobilization'),
        (2, 'green', 'deployment');

--insert UNIT data
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE, PLANNED, CALENDAR_ID)
values  (100, null, 'parent1', false, 1),
        (200, 100, 'child1', false, 1),
        (300, 100, 'child2', false, 1);

--insert EVENT_DURATION data
INSERT INTO PUBLIC.EVENT_DURATION (EVENT_TYPE_ID, DURATION, UNIT_ID)
values  (1, 5, 200),
        (2, 6, 300);