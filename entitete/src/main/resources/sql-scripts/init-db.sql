INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, kvadratov_po_osebi, kvadratura) VALUES ('Trgovina', 2, 0, 10, 200);
INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, kvadratov_po_osebi, kvadratura) VALUES ('Lekarna', 1, 3, 20, 50);

INSERT INTO vrata (st_vstopov, st_izstopov, prostor_id) VALUES (20, 11, 1);
INSERT INTO vrata (st_vstopov, st_izstopov, prostor_id) VALUES (9, 18, 1);

INSERT INTO vrata (st_vstopov, st_izstopov, prostor_id) VALUES (36, 36, 2);

INSERT INTO zaposleni (vzdevek, ime, priimek, vrata_id) VALUES ('shak', 'Shaquille', 'ONeal', 1);
INSERT INTO zaposleni (vzdevek, ime, priimek, vrata_id) VALUES ('hak', 'Hakeem', 'Olajuwon', 2);
INSERT INTO zaposleni (vzdevek, ime, priimek, vrata_id) VALUES ('hook', 'Kareem', 'Abdul-Jabbar', 3);