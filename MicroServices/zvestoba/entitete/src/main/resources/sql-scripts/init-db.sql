INSERT INTO uporabniki (ime, priimek, uporabnisko_ime, email) VALUES ('Petra', 'Kos', 'petrakos', 'petra.kos@hotmail.com');
INSERT INTO uporabniki (ime, priimek, uporabnisko_ime, email) VALUES ('Miha', 'Novak', 'mihanovak', 'miha.novak@gmail.com');

INSERT INTO storitve (naziv, ponudnikId, opis, stpridobljenihtock) VALUES ('Storitev 1', '0', 'Poljuben opis storitve 1, ki poveca tocke za 1', '1');
INSERT INTO storitve (naziv, ponudnikId, opis, stpridobljenihtock) VALUES ('Storitev 2', '1', 'Poljuben opis storitve 2, ki poveca tocke za 2', '2');

INSERT INTO zbrane_tocke (uporabnisko_ime, storitevid, st_tock) VALUES ('petrakos', '1', '5');
INSERT INTO zbrane_tocke (uporabnisko_ime, storitevid, st_tock) VALUES ('petrakos', '2', '1');
INSERT INTO zbrane_tocke (uporabnisko_ime, storitevid, st_tock) VALUES ('mihanovak', '1', '2');
