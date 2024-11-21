DO
'
    BEGIN
        IF NOT EXISTS(SELECT FROM users WHERE user_id=''user_uuid'') THEN
            INSERT INTO users(
                user_email,
                user_authorities,
                user_avatar,
                user_email_confirmed,
                user_favorite_airfield,
                user_name,
                user_password,
                user_last_connection,
                user_id
            )
            VALUES (
                   ''test@gmail.com'',
                   ''{BUDDING_PILOT}'',
                   ''PILOT_MAN'',
                   true,
                   (SELECT airfield_id
                    FROM airfields
                    WHERE airfield_code = ''OW''),
                   ''serkox'',
                   ''$2a$10$pFTVqks22uU99oHtMF3mX.nSIGbYhxpUB6tb9mb6cFKvTt3Y0QYai'',
                   ''2024-11-19 21:25:34.461294'',
                   ''user_uuid''
            );

            INSERT INTO aircrafts (
               aircraft_id,
               aircraft_base_factor,
               aircraft_consumption,
               aircraft_fuel_type,
               aircraft_is_favorite,
               aircraft_lever_arm_back_seat,
               aircraft_lever_arm_front_seat,
               aircraft_lever_arm_fuel,
               aircraft_lever_arm_luggage,
               aircraft_lever_arm_unloaded_weight,
               aircraft_maximum_weight,
               aircraft_model,
               aircraft_non_pumpable_fuel,
               aircraft_registration,
               aircraft_tank_capacity,
               aircraft_true_air_speed,
               aircraft_unloaded_weight,
               user_id,
               aircraft_is_common
            )
            VALUES (1, 0.6,
                    25, 1,
                    true, null,
                    null, null,
                    null, null,
                    900, ''DR300'',
                    1, ''F-TEST'',
                    110, 120,
                    574, ''user_uuid'',
                    false
            );

        END IF;
    END
' LANGUAGE plpgsql;