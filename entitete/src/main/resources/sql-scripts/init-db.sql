INSERT INTO zaposleni (ime, priimek) VALUES ('Shaquille', 'ONeal');
INSERT INTO zaposleni (ime, priimek) VALUES ('Hakeem', 'Olajuwon');
INSERT INTO zaposleni (ime, priimek) VALUES ('Kareem', 'Abdul-Jabbar');

INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, omejitev_oseb) VALUES ('Trgovina', 2, 0, 10);

INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, omejitev_oseb) VALUES ('Lekarna', 1, 2, 5);

INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (20, 11, 1, 1);
INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (9, 18, 1, 2);

INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (36, 36, 2, 3);
