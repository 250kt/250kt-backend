INSERT INTO public.aircrafts (aircraft_id, aircraft_base_factor,
                              aircraft_consumption, aircraft_fuel_type,
                              aircraft_is_favorite, aircraft_lever_arm_back_seat,
                              aircraft_lever_arm_front_seat, aircraft_lever_arm_fuel,
                              aircraft_lever_arm_luggage, aircraft_lever_arm_unloaded_weight,
                              aircraft_maximum_weight, aircraft_model,
                              aircraft_non_pumpable_fuel, aircraft_registration,
                              aircraft_tank_capacity, aircraft_true_air_speed,
                              aircraft_unloaded_weight, user_id,
                              aircraft_is_common)
VALUES (DEFAULT, 0.6,
        25, 1,
        false, null,
        null, null,
        null, null,
        900, 'DR400',
        1, 'F-GOFLY',
        110, 120,
        574, null,
        true);

