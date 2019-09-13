
--insert EVENT_TYPE data
INSERT INTO PUBLIC.EVENT_TYPE (COLOR, DESCRIPTION)
values ('red', 'mobilization');
INSERT INTO PUBLIC.EVENT_TYPE (COLOR, DESCRIPTION)
values ('green', 'deployment');

--insert UNIT data
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE)
values (100, null, 'parent1');
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE)
values (200, 1, 'child1');
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE)
values (300, 1, 'child2');

--insert EVENT_DURATION data
INSERT INTO PUBLIC.EVENT_DURATION (EVENT_TYPE_ID, DURATION, UNIT_ID)
values (1, 5, 200);
INSERT INTO PUBLIC.EVENT_DURATION (EVENT_TYPE_ID, DURATION, UNIT_ID)
values (2, 6, 300);