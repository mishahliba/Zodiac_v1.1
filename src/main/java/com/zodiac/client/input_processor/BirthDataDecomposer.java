package com.zodiac.client.input_processor;

import com.zodiac.client.enums.ErrorMessages;
import com.zodiac.client.model.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Designed to extract constituent elements from input and prepare data for Date creation. Validates for critical errors.
 */
public class BirthDataDecomposer {

    private static final Logger LOG = LoggerFactory.getLogger(BirthDataDecomposer.class);
    private static final String INPUT_SPLITTER = "[^0-9-]";
    private static final UserBirthData USER_BIRTH_DATA = new UserBirthData();
    private static final String DELIMITER = "-";
    private static final int EXPECTED_INPUT_LENGTH = 10;
    private String trimmedUserInput = "";

    public Notifications layOutBirthData(String birthData, Notifications notifications) {
        removeExtraSymbols(birthData);
        if (trimmedUserInput != "" && trimmedUserInput.length() == EXPECTED_INPUT_LENGTH) {
            String[] splittedInput = trimmedUserInput.split(DELIMITER);
            this.USER_BIRTH_DATA.setDay(splittedInput[0]);
            this.USER_BIRTH_DATA.setMonth(splittedInput[1]);
            this.USER_BIRTH_DATA.setYear(splittedInput[2]);
            LOG.info("user input parsed successfully. day {}, month {}, year {} ",
                    this.USER_BIRTH_DATA.day, this.USER_BIRTH_DATA.month, this.USER_BIRTH_DATA.year);
        } else {
            LOG.error("wrong user input. date format cannot be parsed. date: {} ", birthData);
            notifications.addError(ErrorMessages.WRONG_INPUT_LENGTH.getMessage());
        }
        return notifications;
    }

    public String removeExtraSymbols(String userInput) {
        userInput = userInput.replaceAll(INPUT_SPLITTER, "").trim();
        if (!userInput.equals("")) {
            trimmedUserInput = userInput;
        }
        return trimmedUserInput;
    }

    public String getYear() {
        return USER_BIRTH_DATA.year;
    }

    public String getMonth() {
        return USER_BIRTH_DATA.month;
    }

    public String getDay() {
        return USER_BIRTH_DATA.day;
    }
}

