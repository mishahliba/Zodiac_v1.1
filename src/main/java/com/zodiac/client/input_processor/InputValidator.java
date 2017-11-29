package com.zodiac.client.input_processor;

import com.zodiac.client.enums.ErrorMessages;

import java.time.LocalDate;

import com.zodiac.client.model.Notifications;
import com.zodiac.client.model.ValidInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Validates user input for any possible non-critical errors. Throws exception immediately if some critical error happens
 * and further validation isn`t possible. Otherwise pass input through validation chain and collects error message.
 */
public class InputValidator {

    private static final Logger LOG = LoggerFactory.getLogger(InputValidator.class);
    private static final Integer OLDEST_PERSON_BIRTHDAY_YEAR = 1900;
    private BirthDataDecomposer decomposer;
    public Notifications notifications = new Notifications();

    public InputValidator(String userInput) {
        decomposer = new BirthDataDecomposer();
        decomposer.layOutBirthData(userInput, notifications);
        LOG.debug("decomposed birth data: day {}, month {}, year {}",
                decomposer.getDay(), decomposer.getMonth(), decomposer.getYear());
    }

    public ValidInput getValidInput() {
        int day = Integer.valueOf(decomposer.getDay());
        int month = Integer.valueOf(decomposer.getMonth());
        return new ValidInput(day, month);
    }

    public Notifications checkUserInput() {
        if (notifications.hasErrors()) return notifications;
        checkDateCorrectness();
        checkYearCorrectness();
        return notifications;
    }

    private void checkYearCorrectness() {
        validateOldness(notifications);
        validateYouth(notifications);
    }

    private void checkDateCorrectness() {
        validateCorrectMonth(notifications);
        validateCorrectDay(notifications);
    }

    public void validateOldness(Notifications notifications) {
        Integer year = Integer.valueOf(decomposer.getYear());
        if (year < OLDEST_PERSON_BIRTHDAY_YEAR) {
            notifications.addError(ErrorMessages.IMPOSSIBLY_LONG_LIVING_PERSON.getMessage());
            LOG.debug("user year input incorrect: {} ", year);
        }
    }

    public void validateYouth(Notifications notifications) {
        LocalDate today = LocalDate.now();
        int userInputYear = Integer.valueOf(decomposer.getYear());
        int userInputMonth = Integer.valueOf(decomposer.getMonth());
        int userInputDay = Integer.valueOf(decomposer.getDay());
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        if ((year < userInputYear) ||
                (year == userInputYear && month < userInputMonth) ||
                (year == userInputYear && month == userInputMonth && day < userInputDay)) {
            notifications.addError(ErrorMessages.BORN_IN_FUTURE.getMessage());
            LOG.debug("user input incorrect. born tomorrow or later. year {}, month {}, day {} ",
                    userInputYear, userInputMonth, userInputDay);
        }
    }

    public void validateCorrectMonth(Notifications notifications) {
        int monthExists = Integer.valueOf(decomposer.getMonth());
        if (monthExists < 1 || monthExists > 12) {
            notifications.addError(ErrorMessages.WRONG_MONTH.getMessage());
            LOG.warn("wrong user input. month not lay between 1 and 12. actual: {}", decomposer.getMonth());
        }
    }

    public void validateCorrectDay(Notifications notifications) {
        int day = Integer.valueOf(decomposer.getDay());
        boolean dayExists = day <= 31 && day > 0;
        if (!dayExists) {
            LOG.warn("wrong user input. impossible day of month: {} ", day);
            notifications.addError(ErrorMessages.DAY_NOT_EXISTS.getMessage());
        }
        int month = Integer.valueOf(decomposer.getMonth());
        boolean lastDayOfMonthIncorrect = validateLastDayOfMonth(month, day);
        boolean leapIncorrect = validateLeapYear(month, day);
        if (lastDayOfMonthIncorrect || leapIncorrect) {
            notifications.addError(ErrorMessages.WRONG_DAY_OR_MONTH.getMessage());
            LOG.warn("wrong user input. ");
        }
    }

    private boolean validateLeapYear(int monthBirth, int dayBirth) {
        if ((monthBirth == 2 &&
                (dayBirth >= 30 || (dayBirth == 29 && Integer.valueOf(decomposer.getYear()) % 4 != 0)))) {
            LOG.warn("wrong user input. impossible date in February. date: {} ", dayBirth);
            return true;
        }
        return false;
    }

    private boolean validateLastDayOfMonth(int monthBirth, int dayBirth) {
        boolean maxMonthDay = dayBirth == 31;
        boolean monthDividesBy2 = monthBirth % 2 == 0;
        if (maxMonthDay && ((monthBirth < 9 && monthDividesBy2) || (monthBirth >= 9 && !monthDividesBy2))) {
            LOG.warn("wrong user input. day and month are incompatible. day {}, month {} ", dayBirth, monthBirth);
            return true;
        }
        return false;
    }
}
