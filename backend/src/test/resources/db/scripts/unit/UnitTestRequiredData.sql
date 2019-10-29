--insert EVENT_TYPE data
INSERT INTO PUBLIC.EVENT_TYPE (TYPE_ID, COLOR, DESCRIPTION)
values (1, 'red', 'mobilization'),
       (2, 'green', 'deployment');

INSERT INTO PUBLIC.EVENT (EVENT_ID, EVENT_TYPE_ID, UNIT_ID, DATE_FROM, DATE_TO,
                          NOTE, LOCATION_NAME, LOCATION_TYPE, PLANNED, CALENDAR_ID)
values (1, 1, 110, '2019-09-26', '2019-09-30', 'NOT NULL', 'Минск-STATICAL', 'STATICAL', false, 1),
       (2, 2, 120, '2019-10-03', '2019-10-05', NULL, 'Минск-STATICAL', 'STATICAL', false, 1),
       (3, 1, 210, '2019-09-04', '2019-09-30', 'NOT NULL', 'Минск-STATICAL', 'STATICAL', false, 2),
       (4, 1, 211, '2019-09-05', '2019-09-30', 'NOT NULL', 'Минск-DISTRICT', 'DISTRICT', false, 2),
       (5, 1, 212, '2019-09-06', '2019-09-30', 'NOT NULL', 'Минск-DISTRICT', 'DISTRICT', false, 2),
       (6, 1, 212, '2019-08-07', '2019-08-30', 'NOT NULL', 'Минск-DISTRICT', 'DISTRICT', false, 2);