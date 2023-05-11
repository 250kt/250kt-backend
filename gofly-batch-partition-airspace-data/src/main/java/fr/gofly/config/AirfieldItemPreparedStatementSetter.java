package fr.gofly.config;

import fr.gofly.model.Airfield;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AirfieldItemPreparedStatementSetter implements ItemPreparedStatementSetter<Airfield> {
    @Override
    public void setValues(Airfield item, PreparedStatement ps) throws SQLException{
        ps.setString(0, item.getAirfieldId());
        ps.setString(6, item.getAirfieldStatut());
        ps.setString(2, item.getAirfieldFullname());
        ps.setString(3, item.getAirfieldMapName());
        ps.setString(5, item.getAirfieldSituation());
        ps.setString(4, item.getAirfieldPhoneNumber());
        ps.setInt(7, item.getAirfield_altitude());
        ps.setBoolean(1, item.isAirfieldAcceptVfr());
        ps.setFloat(8, item.getAirfieldLatitudeARP());
        ps.setFloat(9, item.getAirfieldLongitudeARP());
    }
}
