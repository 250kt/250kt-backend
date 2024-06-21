package fr.gofly.mapper;

import fr.gofly.dto.PartDto;
import fr.gofly.model.Part;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PartToPartDto {

    public PartDto map(Part part){
        return PartDto.builder()
            .id(part.getId())
            .name(part.getName())
            .geometry(part.getGeometry())
            .build();
    }

    private ArrayList<ArrayList<Double>> geometry(String geometry){
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        String[] coordinates = geometry.split(",");
        for(int i = 0; i < coordinates.length; i+=2){
            ArrayList<Double> point = new ArrayList<>();
            point.add(Double.parseDouble(coordinates[i]));
            point.add(Double.parseDouble(coordinates[i+1]));
            result.add(point);
        }
        return result;
    }

}
