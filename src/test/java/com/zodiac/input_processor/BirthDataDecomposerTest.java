package com.zodiac.input_processor;


import com.zodiac.client.input_processor.BirthDataDecomposer;
import com.zodiac.client.model.Notifications;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class BirthDataDecomposerTest {

    BirthDataDecomposer dataDecomposer = new BirthDataDecomposer();

    @Test
    public void removeExtraSymbolsTest(){
        String clearedInput = dataDecomposer.removeExtraSymbols("ljzxfdsj..]]]21-11-1992/..");
        assertEquals("21-11-1992", clearedInput);
    }

    @Test
    public void errorMessageWhenExtraDigitsPresetTest(){
        Notifications notifications = new Notifications();
        String wrongInputExtraDigits="21-11-1992-4243";
        String error = dataDecomposer.layOutBirthData(wrongInputExtraDigits, notifications).errorMessages();
        assertEquals("wrong birth date format.", error);
    }
}
