insert into test_1.car (id, name)
values (1, 'Wendy'),
       (2, 'Brenda'),
       (3, 'Carol'),
       (4, 'Linda'),
       (5, 'Betty'),
       (6, 'Lisa');


insert into test_1.area (id, code, id_car)
values (1, 111, 1),
       (2, 222, 1),
       (3, 333, 2),
       (4, 444, 2);

insert into test_1.action_one (id, id_area, id_type, number_action)
values (1, 1, 1, 1),
       (2, 2, 2, 2);
