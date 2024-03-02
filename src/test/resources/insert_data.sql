INSERT INTO category (id, name, parent_id)
VALUES (1,'Бизнес',null),
       (2,'Компьютерная литература',null),
       (3,'Искусство', null);

INSERT INTO category (id,name, parent_id)
VALUES (4,'Менеджмент',(SELECT id FROM category WHERE name = 'Бизнес')),
       (5,'Маркетинг',(SELECT id FROM category WHERE name = 'Бизнес')),
       (6,'Логистика', (SELECT id FROM category WHERE name = 'Бизнес')),
       (7,'Операционные системы', (SELECT id FROM category WHERE name = 'Компьютерная литература')),
       (8,'Разработка ПО', (SELECT id FROM category WHERE name = 'Компьютерная литература')),
       (9,'Архитектура', (SELECT id FROM category WHERE name = 'Искусство')),
       (10,'Музыка', (SELECT id FROM category WHERE name = 'Искусство'));
SELECT SETVAL('category_id_seq', (SELECT MAX(id) FROM category));

INSERT INTO product (id,category_id, name, description, author, image, price, remaining_amount, created_at)
VALUES (1,(SELECT id FROM category WHERE name = 'Менеджмент'),'Гуру менеджмента','Книга рассказывает о личностях','Колдвелл Дэрил','Users/sanzharshakrat/Desktop/eshop/Гуру менеджмента.jpg',3990,4,CURRENT_TIMESTAMP),
       (2,(SELECT id FROM category WHERE name = 'Менеджмент'),'Менеджмент','Данное издание предлагает читателю краткое и структурированное изложение основного материала по менеджменту.','Хворостенко Александр','Users/sanzharshakrat/Desktop/eshop/Менеджмент.jpg',6220,5,CURRENT_TIMESTAMP),
       (3,(SELECT id FROM category WHERE name = 'Маркетинг'),'Станьте Мастером продаж!','Эта книга Наполеона Хилла','Хилл Наполеон','Users/sanzharshakrat/Desktop/eshop/Станьте Мастером продаж!.jpg',2350,2,CURRENT_TIMESTAMP),
       (4,(SELECT id FROM category WHERE name = 'Маркетинг'),'Как подняться от неудач к успеху в продажах','В этой книге один из самых высокооплачиваемых торговых агентов в мире раскрывает свой секрет заключения сделок.','Беттджер Фрэнк','Users/sanzharshakrat/Desktop/eshop/Как подняться от неудач к успеху в продажах.jpg',2740,6,CURRENT_TIMESTAMP),
       (5,(SELECT id FROM category WHERE name = 'Логистика'),'Задачи маршрутизации перемещений','Учебное пособие посвящено исследованию задач маршрутизации с ограничениями','Сесекин Александр Николаевич','Users/sanzharshakrat/Desktop/eshop/Задачи маршрутизации перемещений.jpg',14790,3,CURRENT_TIMESTAMP),
       (6,(SELECT id FROM category WHERE name = 'Логистика'),'Логистика. Учебное пособие','Учебное пособие знакомит будущих специалистов-маркетологов с теоретическими основами и содержанием логистической деятельности: закупочной','Каменева Н. Г.','Users/sanzharshakrat/Desktop/eshop/Логистика. Учебное пособие.jpg',9840,5,CURRENT_TIMESTAMP),
       (7,(SELECT id FROM category WHERE name = 'Операционные системы'),'Разработка через тестирование для iOS','Гибкий и понятный программный код','Ли Грэхен','Users/sanzharshakrat/Desktop/eshop/Разработка через тестирование для iOS.jpg',7805,4,CURRENT_TIMESTAMP),
       (8,(SELECT id FROM category WHERE name = 'Операционные системы'),'BPF для мониторинга Linux','Виртуальная машина BPF — один из важнейших компонентов ядра Linux. Её грамотное применение позволит системным инженерам находить сбои и решать даже самые сложные проблемы.','Калавера Дэвид','Users/sanzharshakrat/Desktop/eshop/BPF для мониторинга Linux.jpg',12310,9,CURRENT_TIMESTAMP),
       (9,(SELECT id FROM category WHERE name = 'Разработка ПО'),'Bootstrap в примерах','Данная книга содержит различные примеры и пошаговое описание создания различных веб-приложепий с помощью клиентского фреймворка Bootstrap.','Морето Сильвио','Users/sanzharshakrat/Desktop/eshop/Bootstrap в примерах.jpg',11430,7,CURRENT_TIMESTAMP),
       (10,(SELECT id FROM category WHERE name = 'Разработка ПО'),'Проектирование веб-API','API позволяет разработчикам выполнять интеграцию с приложением без детализированного знания кода.','Лоре Арно','Users/sanzharshakrat/Desktop/eshop/Проектирование веб-API.jpg',16330,9,CURRENT_TIMESTAMP),
       (11,(SELECT id FROM category WHERE name = 'Архитектура'),'Архитектура в разрезе','План здания в разрезе - одна из необходимых составляющих любого архитектурного проекта','Льюис Пол','Users/sanzharshakrat/Desktop/eshop/Архитектура в разрезе.jpg',18605,8,CURRENT_TIMESTAMP),
       (12,(SELECT id FROM category WHERE name = 'Архитектура'),'История русской архитектуры','История русской архитектуры» академика Императорской Академии художеств Андрея Павлинова (1852-1897) — это первый профессиональный курс истории русской архитектуры.','Павлинов Андрей Владимирович','Users/sanzharshakrat/Desktop/eshop/История русской архитектуры.jpg',6565,4,CURRENT_TIMESTAMP),
       (13,(SELECT id FROM category WHERE name = 'Музыка'),'Среди музыкантов. Учебное пособие','В книге Леопольд Ауэр (1845-1930)','Ауэр Леопольд','Users/sanzharshakrat/Desktop/eshop/Среди музыкантов. Учебное пособие.jpg',8345,3,CURRENT_TIMESTAMP);
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));