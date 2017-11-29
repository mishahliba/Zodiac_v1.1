package com.zodiac.input_processor;

import com.zodiac.client.exceptions.IncorrectInputException;
import com.zodiac.client.input_processor.InputValidator;
import com.zodiac.client.model.Notifications;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class InputValidatorTest {

    InputValidator validator;

    @Test
    public void extraSymbolsPresetTest() throws IncorrectInputException {
        validator = new InputValidator("..lsdf21-11-1992ds");
        Notifications notifications = validator.checkUserInput();
        assertFalse(notifications.hasErrors());
    }

    @Test
    public void yearWrongPositionTest() throws IncorrectInputException {
        String expectMessage = "month must lay between 1 and 12.";
        Notifications notifications = new Notifications();
        String userInput = "11-1992-12";
        validator = new InputValidator(userInput);
        validator.validateCorrectMonth(notifications);
        assertEquals(expectMessage, notifications.errorMessages());
    }

    @Test
    public void compositeExceptionTest() {
        String expectMessage = "month must lay between 1 and 12.\n" +
                "day must lay between 1 and 31.\n" +
                "you could`t be born in that year.";
        String userInput = "32-14-1899";
        validator = new InputValidator(userInput);
        Notifications notifications = validator.checkUserInput();
        assertEquals(expectMessage, notifications.errorMessages());
    }
}
