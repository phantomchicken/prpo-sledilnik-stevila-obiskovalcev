INSERT INTO zaposleni (ime, priimek) VALUES ('Shaquille', 'ONeal');
INSERT INTO zaposleni (ime, priimek) VALUES ('Hakeem', 'Olajuwon');
INSERT INTO zaposleni (ime, priimek) VALUES ('Kareem', 'Abdul-Jabbar');

INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, kvadratov_po_osebi, kvadratura) VALUES ('Trgovina', 2, 0, 10, 200);

INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, kvadratov_po_osebi, kvadratura) VALUES ('Lekarna', 1, 3, 20, 50);

INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (20, 11, 1, 1);
INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (9, 18, 1, 2);

INSERT INTO vrata (st_vhodov, st_izhodov, prostor_id, zaposleni_id) VALUES (36, 36, 2, 3);
