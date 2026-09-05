INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

-- Top Bollywood Artists
INSERT INTO artists (id, name, bio, image_url) VALUES
(1, 'Arijit Singh', 'The undisputed voice of romantic Bollywood ballads with hundreds of chart-topping hits.', 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/ce/22/ec/ce22ecf6-4074-a0c4-f06b-4e0c4cb03e48/8902894361515_cover.jpg/600x600bb.jpg'),
(2, 'Shreya Ghoshal', 'Multiple National Film Award-winning legendary playback singer with unparalleled vocal range.', 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/ec/3b/b7/ec3bb748-eb6e-e962-d278-f94e9f56477e/196589332219.jpg/600x600bb.jpg'),
(3, 'Pritam', 'Master hitmaker and composer behind some of Indian cinema''s most iconic soundtracks.', 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/95/92/ff/9592ff41-11d9-c020-f5ea-6bb5a1b3265a/8901858055627.jpg/600x600bb.jpg'),
(4, 'Atif Aslam', 'Renowned Pakistani playback singer famous for soulful vocals and emotional melodies.', 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/07/ee/12/07ee123b-01ee-2dc5-bf7d-5e263d9171e5/197188737687.jpg/600x600bb.jpg'),
(5, 'Diljit Dosanjh', 'Global Punjabi superstar, actor, and singer blending Punjabi folk with modern hip-hop.', 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/db/4f/98/db4f981e-1f54-5264-77ad-0870347318ec/197189196940.jpg/600x600bb.jpg'),
(6, 'Neha Kakkar', 'High-energy playback powerhouse delivering Bollywood''s most memorable party and dance anthems.', 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/10/d7/89/10d7890b-6893-cb5c-42b7-aeb8972df54d/8903247070119.jpg/600x600bb.jpg'),
(7, 'Darshan Raval', 'Indie pop sensation and playback artist acclaimed for heartfelt romantic melodies.', 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/58/cb/09/58cb0903-8e45-d866-9e66-9b939e0eb9df/196589255655.jpg/600x600bb.jpg'),
(8, 'Jubin Nautiyal', 'Sensational playback singer known for soulful spiritual and romantic tracks.', 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/ff/82/38/ff823871-337d-bfa2-36fb-e4f6dfc79213/197187158735.jpg/600x600bb.jpg');

-- Iconic Bollywood Movie Albums
INSERT INTO albums (id, title, release_year, artist_id, cover_url) VALUES
(1, 'Brahmastra', 2022, 3, 'https://is1-ssl.mzstatic.com/image/thumb/Music112/v4/95/92/ff/9592ff41-11d9-c020-f5ea-6bb5a1b3265a/8901858055627.jpg/600x600bb.jpg'),
(2, 'Jawan', 2023, 1, 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/df/27/ef/df27ef20-8ee2-b4ea-46b5-859a85012353/197189178342.jpg/600x600bb.jpg'),
(3, 'Aashiqui 2', 2013, 1, 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/ce/22/ec/ce22ecf6-4074-a0c4-f06b-4e0c4cb03e48/8902894361515_cover.jpg/600x600bb.jpg'),
(4, 'Animal', 2023, 1, 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/71/84/cf/7184cf43-f8a4-a9b7-1c39-2a9db51d388f/197189429185.jpg/600x600bb.jpg'),
(5, 'Bhediya', 2022, 1, 'https://is1-ssl.mzstatic.com/image/thumb/Music122/v4/0f/ce/f0/0fcef057-a9a7-96b6-9993-94c6f2ee876d/8902894361591_cover.jpg/600x600bb.jpg'),
(6, 'Dunki', 2023, 3, 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/58/df/31/58df318e-49ff-0580-c116-3914946da246/197189531581.jpg/600x600bb.jpg'),
(7, 'Shershaah', 2021, 8, 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/ff/82/38/ff823871-337d-bfa2-36fb-e4f6dfc79213/197187158735.jpg/600x600bb.jpg'),
(8, 'G.O.A.T.', 2020, 5, 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/db/4f/98/db4f981e-1f54-5264-77ad-0870347318ec/197189196940.jpg/600x600bb.jpg'),
(9, 'Yeh Jawaani Hai Deewani', 2013, 3, 'https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/5d/ce/64/5dce64e4-7d52-9b2a-14d2-f6aebeebfaea/8901858022711.jpg/600x600bb.jpg'),
(10, 'Kabir Singh', 2019, 1, 'https://is1-ssl.mzstatic.com/image/thumb/Music116/v4/21/df/f6/21dff625-f3ff-5444-a950-8b1a37c02b3c/8902894356764_cover.jpg/600x600bb.jpg');

-- Curated Top Bollywood Songs with verified streams
INSERT INTO songs (id, title, duration_seconds, genre, audio_url, artist_id, album_id) VALUES
(1, 'Kesariya', 268, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/ad/86/f4/ad86f4bc-b7a0-945a-c71a-b4d0e839baea/mzaf_12060955137338918318.plus.aac.p.m4a', 1, 1),
(2, 'Chaleya', 200, 'Pop / Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/76/05/d9/7605d905-f631-517d-df7f-e162affcd414/mzaf_9976541859961700749.plus.aac.p.m4a', 1, 2),
(3, 'Tum Hi Ho', 262, 'Soulful', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/38/de/b9/38deb942-d44a-f2bb-205c-ddf05be84693/mzaf_9747647124859107103.plus.aac.p.m4a', 1, 3),
(4, 'Satranga', 271, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/f9/c4/db/f9c4db6d-efa0-0b8b-c80a-046b06499f2b/mzaf_3150095649021462379.plus.aac.p.m4a', 1, 4),
(5, 'Apna Bana Le', 261, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/eb/27/61/eb2761c7-d606-0912-dff0-2dc6b69974bd/mzaf_2023722930851223219.plus.aac.p.m4a', 1, 5),
(6, 'O Maahi', 233, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/a9/82/78/a9827837-5ca7-1fe3-fc24-3dedeffb86e4/mzaf_4678729972431007397.plus.aac.p.m4a', 1, 6),
(7, 'Raataan Lambiyan', 230, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/99/0c/38/990c381b-0530-8c0d-87a9-18b050b97f0a/mzaf_10418866714500530894.plus.aac.p.m4a', 8, 7),
(8, 'G.O.A.T.', 223, 'Punjabi / Hip-Hop', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/ba/5c/57/ba5c5769-536b-1884-dfbf-5930cab13332/mzaf_7745269360294572986.plus.aac.p.m4a', 5, 8),
(9, 'Ilahi', 229, 'Travel / Acoustic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/75/27/64/75276455-6751-e9d7-1187-b863a2ffefbc/mzaf_4313837125483855343.plus.aac.p.m4a', 1, 9),
(10, 'Bekhayali', 371, 'Rock / Ballad', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/93/fb/67/93fb67c1-7de6-f062-21e0-ed4babc196a3/mzaf_8590895579211855037.plus.aac.p.m4a', 1, 10),
(11, 'Kabira', 251, 'Folk / Sufi', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/e2/06/19/e2061998-6444-5c5b-5bfc-a149c55e2e2e/mzaf_10494977375651598168.plus.aac.p.m4a', 1, 9),
(12, 'Zaalima', 299, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/cd/9f/4a/cd9f4a3f-8f5d-922b-2db9-933751017f8f/mzaf_8736831722992033377.plus.aac.p.m4a', 1, 3),
(13, 'Lover', 190, 'Punjabi Pop', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/38/d5/7a/38d57a99-39fc-e901-7c45-fa6260ec83c1/mzaf_8083696285926392389.plus.aac.p.m4a', 5, 8),
(14, 'Agar Tum Saath Ho', 341, 'Melancholy', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview221/v4/75/a8/7d/75a87dcc-5b69-795d-7dcc-27d1c728f31f/mzaf_18055325784732588932.plus.aac.p.m4a', 1, 9),
(15, 'Deva Deva', 279, 'Spiritual Pop', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/24/61/2a/24612a7d-c42b-90a5-c980-07f4db2eee6f/mzaf_4575238190575326306.plus.aac.p.m4a', 1, 1),
(16, 'Hawayein', 290, 'Acoustic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/3d/31/13/3d311382-d94e-4f98-2437-1e5ca0440f27/mzaf_12854504686630749705.plus.aac.p.m4a', 1, 9),
(17, 'Pyaar Hota Kayi Baar Hai', 216, 'Dance Pop', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview116/v4/90/d4/90/90d4907e-5c2a-b2e0-a0ad-f4712fadb6a0/mzaf_9524985315628052523.plus.aac.p.m4a', 1, 2),
(18, 'Badtameez Dil', 252, 'Party / Dance', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/88/e2/af/88e2af36-3b58-3f34-32c3-d94d3086c8d5/mzaf_15476348080733865142.plus.aac.p.m4a', 6, 9),
(19, 'Teri Ore', 339, 'Romantic', 'https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview211/v4/72/1e/13/721e13a2-7ea0-1dfb-7d9c-c5bbcf1f4df4/mzaf_14014711574677331364.plus.aac.p.m4a', 2, 9);
