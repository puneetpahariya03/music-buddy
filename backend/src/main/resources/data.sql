INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO artists (id, name, bio, image_url) VALUES
(1, 'Lobo Loco', 'Acclaimed ambient, jazz, and blues composer with over 50 million streams worldwide.', 'https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=600&q=80'),
(2, 'Scott Holmes Music', 'Prolific indie-pop, folk, and upbeat acoustic producer featuring uplifting melodies.', 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?auto=format&fit=crop&w=600&q=80'),
(3, 'Broke For Free', 'Pioneering electronic, synthwave, and chillhop producer known for mellow beats and smooth rhythms.', 'https://images.unsplash.com/photo-1514525253161-7a46d19cd819?auto=format&fit=crop&w=600&q=80'),
(4, 'Kai Engel', 'Classically trained neo-classical composer renowned for cinematic piano arrangements.', 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?auto=format&fit=crop&w=600&q=80');

INSERT INTO albums (id, title, release_year, artist_id, cover_url) VALUES
(1, 'Sunset Overdrive', 2022, 1, 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=600&q=80'),
(2, 'Upbeat Journeys', 2023, 2, 'https://images.unsplash.com/photo-1511379938547-c1f69419868d?auto=format&fit=crop&w=600&q=80'),
(3, 'Petals & Bass', 2021, 3, 'https://images.unsplash.com/photo-1518609878373-06d740f60d8b?auto=format&fit=crop&w=600&q=80'),
(4, 'Chapter One: Solitude', 2020, 4, 'https://images.unsplash.com/photo-1507838153414-b4b713384a76?auto=format&fit=crop&w=600&q=80');

-- Real full-length (3 to 4+ minutes) direct cloud streaming songs (No download required)
INSERT INTO songs (id, title, duration_seconds, genre, audio_url, artist_id, album_id) VALUES
(1, 'Night Owl', 221, 'Electronic', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/WFMU/Broke_For_Free/Directionless_EP/Broke_For_Free_-_01_-_Night_Owl.mp3', 3, 3),
(2, 'Storybook', 184, 'Pop / Acoustic', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/no_curator/Scott_Holmes/Inspiring__Upbeat_Music/Scott_Holmes_-_04_-_Storybook.mp3', 2, 2),
(3, 'Summer Spliffs', 255, 'Chillout', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Broke_For_Free/Petal/Broke_For_Free_-_01_-_Summer_Spliffs.mp3', 3, 3),
(4, 'Great Expectations', 214, 'Acoustic', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/no_curator/Scott_Holmes/Inspiring__Upbeat_Music/Scott_Holmes_-_05_-_Great_Expectations.mp3', 2, 2),
(5, 'Headway', 198, 'Classical', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Kai_Engel/Sustenance/Kai_Engel_-_04_-_Headway.mp3', 4, 4),
(6, 'Brain Drain', 230, 'Blues / Rock', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/ccCommunity/Lobo_Loco/Not_my_Style/Lobo_Loco_-_01_-_Brain_Drain_ID_1300.mp3', 1, 1),
(7, 'Something Elated', 195, 'Indie', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/WFMU/Broke_For_Free/Something_EP/Broke_For_Free_-_05_-_Something_Elated.mp3', 3, 3),
(8, 'Drive', 242, 'Cinematic', 'https://files.freemusicarchive.org/storage-freemusicarchive-org/music/no_curator/Scott_Holmes/Corporate__Motivational_Music/Scott_Holmes_-_01_-_Drive.mp3', 2, 2);
