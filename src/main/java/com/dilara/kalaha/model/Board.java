package com.dilara.kalaha.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Board {

    private List<Pit> pits = new ArrayList<>();

    public List<Pit> getPits() {
        return pits;
    }

    public void setPits(List<Pit> pits) {
        this.pits = pits;
    }

    public Pit getPit(Integer id) {
        try {
            return pits.get(id);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(String.format("Id %d is invalid", id));
        }
    }

}
