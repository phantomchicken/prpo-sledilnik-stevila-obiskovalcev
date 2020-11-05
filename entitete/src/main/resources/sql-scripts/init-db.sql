INSERT INTO zaposleni (ime, priimek, vrata_id_fk) VALUES ("Shaquille", "O'Neal", 1);
INSERT INTO zaposleni (ime, priimek, vrata_id_fk) VALUES ("Hakeem", "Olajuwon", 2);
INSERT INTO vrata (st_vhodov, st_izhodov, zaposleni_id_fk) VALUES (20, 11, 1);
INSERT INTO vrata (st_vhodov, st_izhodov, zaposleni_id_fk) VALUES (9, 18, 2);
INSERT INTO prostor (ime_prostora, st_vrat, trenutno_oseb, omejitev_oseb) VALUES ("Trgovina", 2, 0, 10);