package com.zodiac.client.model;

import java.util.List;

/**
 * Class designed to contain aggregated user input validation errors.
 */
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Notifications {
    private List<String> errors = new ArrayList<>();

    public void addError(String message){
        errors.add(message);
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public String errorMessages(){
        return errors.stream().collect(Collectors.joining("\n "));
    }
}
