--EventTestRequiredData.sql has to be executed before this insert
INSERT INTO PUBLIC.EVENT (EVENT_ID, EVENT_TYPE_ID, UNIT_ID, DATE_FROM, DATE_TO, NOTE, LOCATION_NAME, LOCATION_TYPE, PLANNED, CALENDAR_ID)
values (1, 1, 200, '2019-09-26', '2019-09-30', 'NOT NULL', 'Минск', 'STATICAL', false, 1),
       (2, 2, 300, '2019-10-03', '2019-10-05', NULL, 'Минск', 'STATICAL', false, 1);