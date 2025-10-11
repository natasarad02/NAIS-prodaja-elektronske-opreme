
-- -------------------- USER TYPES --------------------
INSERT INTO user_type (id, name, description) VALUES
                                                  (1, 'Distributer',  'Korisnici iz veleprodaje'),
                                                  (2, 'Maloprodaja',  'Krajnji kupci (retail)'),
                                                  (3, 'Partner',      'Poslovni partneri / reseleri'),
                                                  (4, 'Enterprise',   'Veliki B2B kupci'),
                                                  (5, 'Interni',      'Interni korisnici (demo/test)')
    ON CONFLICT (id) DO NOTHING;


-- -------------------- LIFECYCLE PHASES (druga tabela) --------------------
INSERT INTO lifecycle_phases (id, title, position, description) VALUES
                                                                    (1, 'Draft',      1, 'Inicijalna skica – još se ne koristi u procesu'),
                                                                    (2, 'Prototype',  2, 'Prototip – validacija koncepta'),
                                                                    (3, 'QA',         3, 'Testiranje i verifikacija kvaliteta'),
                                                                    (4, 'Production', 4, 'Aktivna produkcijska faza'),
                                                                    (5, 'Retired',    5, 'Povučeno iz upotrebe')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO lifecycle_phase_next (phase_id, next_phase_id) VALUES
                                                               (1, 2),
                                                               (2, 1), (2, 3),
                                                               (3, 2), (3, 4),
                                                               (4, 3), (4, 5),
                                                               (5, 4)
    ON CONFLICT DO NOTHING;

-- -------------------- REGIONS --------------------
INSERT INTO regions (id, name) VALUES
                                   (1, 'Srbija'),
                                   (2, 'Crna Gora'),
                                   (3, 'Evropska unija')
    ON CONFLICT (id) DO NOTHING;

INSERT INTO region_cities (region_id, city_name) VALUES
                                                     (1, 'Beograd'),
                                                     (1, 'Novi Sad'),
                                                     (1, 'Niš'),
                                                     (1, 'Kragujevac'),
                                                     (1, 'Subotica'),
                                                     (2, 'Podgorica'),
                                                     (2, 'Nikšić'),
                                                     (2, 'Bar')
    ON CONFLICT DO NOTHING;

INSERT INTO region_countries (region_id, country_name) VALUES
                                                           (1, 'Srbija'),
                                                           (2, 'Crna Gora'),
                                                           (3, 'Nemačka'),
                                                           (3, 'Francuska'),
                                                           (3, 'Italija'),
                                                           (3, 'Španija'),
                                                           (3, 'Poljska'),
                                                           (3, 'Holandija')
    ON CONFLICT DO NOTHING;

