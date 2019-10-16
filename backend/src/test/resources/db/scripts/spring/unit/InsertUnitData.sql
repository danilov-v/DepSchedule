--UnitTestRequiredData.sql has to be executed before this insert
INSERT INTO PUBLIC.UNIT (UNIT_ID, PARENT_ID, TITLE, PLANNED)
values  (100, null, 'parent1', false),
        (200, 1, 'child1', false),
        (300, 1, 'child2', false);