package com.zodiac.client;

import com.zodiac.client.enums.ZodiacSign;
import com.zodiac.client.exceptions.IncorrectInputException;
import com.zodiac.client.input_processor.InputValidator;
import com.zodiac.client.model.Notifications;
import com.zodiac.client.model.ValidInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class responsible for interacting with user.
 */
public class Client {

    static Logger LOG = LoggerFactory.getLogger(Client.class);
    private static InputValidator userInputValidator;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("0")) {
            LOG.info("got user input: {} ", line);
            System.out.println("press '0' to exit");
            userInputValidator = new InputValidator(line);

            try {
                Notifications validationNotifications = userInputValidator.checkUserInput();
                if (validationNotifications.hasErrors()) {
                    throw new IncorrectInputException(validationNotifications.errorMessages());
                } else {
                    ValidInput zodiacInput = userInputValidator.getValidInput();
                    ZodiacSign userZodiacSign = ZodiacSign.getZodiac(zodiacInput);
                    System.out.println("Thanks, you zodiac is " + userZodiacSign);
                }
            } catch (IncorrectInputException e) {
                LOG.warn("user input has errors. can`t be processed futher. errors {} ", e.getMessage());
                System.out.println(e.getMessage());
            }

            line = scanner.nextLine();
        }
    }
}
