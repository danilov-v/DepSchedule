
--insert EVENT_TYPE data
INSERT INTO PUBLIC.EVENT_TYPE (TYPE_ID, COLOR, DESCRIPTION)
values  (1, 'red', 'mobilization'),
        (2, 'green', 'deployment');

--insert UNIT data
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE, PLANNED)
values  (100, null, 'parent1', false),
        (200, 1, 'child1', false),
        (300, 1, 'child2', false);

--insert EVENT_DURATION data
INSERT INTO PUBLIC.EVENT_DURATION (EVENT_TYPE_ID, DURATION, UNIT_ID)
values  (1, 5, 200),
        (2, 6, 300);