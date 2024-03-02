--liquibase formatted sql

--changeset shakratsanzhar:1
INSERT INTO category (name, parent_id)
VALUES ('Бизнес', null),
       ('Компьютерная литература', null),
       ('Искусство', null);

--changeset shakratsanzhar:2
INSERT INTO category (name, parent_id)
VALUES ('Менеджмент', (SELECT id FROM category WHERE name = 'Бизнес')),
       ('Маркетинг', (SELECT id FROM category WHERE name = 'Бизнес')),
       ('Логистика', (SELECT id FROM category WHERE name = 'Бизнес')),
       ('Операционные системы', (SELECT id FROM category WHERE name = 'Компьютерная литература')),
       ('Разработка ПО', (SELECT id FROM category WHERE name = 'Компьютерная литература')),
       ('Архитектура', (SELECT id FROM category WHERE name = 'Искусство')),
       ('Музыка', (SELECT id FROM category WHERE name = 'Искусство'));

--changeset shakratsanzhar:3
INSERT INTO product (category_id, name, description, author, image, price, remaining_amount, created_at)
VALUES ((SELECT id FROM category WHERE name = 'Менеджмент'), 'Гуру менеджмента', 'Книга рассказывает о личностях',
        'Колдвелл Дэрил', 'Guru_menedzhmenta.jpg', 3990, 4, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Менеджмент'), 'Менеджмент',
        'Данное издание предлагает читателю краткое и структурированное изложение основного материала по менеджменту.',
        'Хворостенко Александр', 'Menedzhment.jpg', 6220, 5, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Маркетинг'), 'Станьте Мастером продаж!', 'Эта книга Наполеона Хилла',
        'Хилл Наполеон', 'Stante_masterom_prodazh.jpg', 2350, 2, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Маркетинг'), 'Как подняться от неудач к успеху в продажах',
        'В этой книге один из самых высокооплачиваемых торговых агентов в мире раскрывает свой секрет заключения сделок.',
        'Беттджер Фрэнк', 'Kak_podnyatsya_ot_neudach_k_uspekhu_prodaj.jpg', 2740, 6, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Логистика'), 'Задачи маршрутизации перемещений',
        'Учебное пособие посвящено исследованию задач маршрутизации с ограничениями', 'Сесекин Александр Николаевич',
        'Zadachi_marshrutizacii_peremesheniy.jpg', 14790, 3, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Логистика'), 'Логистика. Учебное пособие',
        'Учебное пособие знакомит будущих специалистов-маркетологов с теоретическими основами и содержанием логистической деятельности: закупочной',
        'Каменева Н. Г.', 'Logistika_uchebnoe_posobie.jpg', 9840, 5, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Операционные системы'), 'Разработка через тестирование для iOS',
        'Гибкий и понятный программный код', 'Ли Грэхен', 'Razrabotka_cherez_testirovanie_ios.jpg', 7805, 4,
        CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Операционные системы'), 'BPF для мониторинга Linux',
        'Виртуальная машина BPF — один из важнейших компонентов ядра Linux. Её грамотное применение позволит системным инженерам находить сбои и решать даже самые сложные проблемы.',
        'Калавера Дэвид', 'BPF_dlya_monitoringa_linux.jpg', 12310, 9, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Разработка ПО'), 'Bootstrap в примерах',
        'Данная книга содержит различные примеры и пошаговое описание создания различных веб-приложепий с помощью клиентского фреймворка Bootstrap.',
        'Морето Сильвио', 'Bootstrap_v_primerah.jpg', 11430, 7, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Разработка ПО'), 'Проектирование веб-API',
        'API позволяет разработчикам выполнять интеграцию с приложением без детализированного знания кода.',
        'Лоре Арно', 'Proektirovanie_web_api.jpg', 16330, 9, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Архитектура'), 'Архитектура в разрезе',
        'План здания в разрезе - одна из необходимых составляющих любого архитектурного проекта', 'Льюис Пол',
        'Arkhitektura_v_razreze.jpg', 18605, 8, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Архитектура'), 'История русской архитектуры',
        'История русской архитектуры» академика Императорской Академии художеств Андрея Павлинова (1852-1897) — это первый профессиональный курс истории русской архитектуры.',
        'Павлинов Андрей Владимирович', 'Istoriya_russkoy_arkhitektury.jpg', 6565, 4, CURRENT_TIMESTAMP),
       ((SELECT id FROM category WHERE name = 'Музыка'), 'Среди музыкантов. Учебное пособие',
        'В книге Леопольд Ауэр (1845-1930)', 'Ауэр Леопольд', 'Sredi_muzykantov_uchebnoe_posobie.jpg', 8345, 3,
        CURRENT_TIMESTAMP);