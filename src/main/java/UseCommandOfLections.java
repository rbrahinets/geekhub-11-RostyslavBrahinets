import exceptions.LessonNotFoundException;
import exceptions.ValidationException;
import models.Lection;

import java.util.Scanner;

public class UseCommandOfLections {
    private final Scanner scanner = new Scanner(System.in);

    public void showAllLections(Lection[] lections) {
        if (lections == null) {
            return;
        }

        for (Lection lection : lections) {
            System.out.println(lection.getName());
        }
    }

    public Lection[] addNewLection(Lection[] lections, String nameOfLection) {
        if (lections == null) {
            lections = new Lection[0];
        }

        if (nameOfLection == null
            || nameOfLection.isEmpty()
            || nameOfLection.isBlank()) {
            throw new ValidationException("Inputted Invalid Data");
        }

        Lection[] newLections = new Lection[lections.length + 1];

        for (int i = 0; i < lections.length; i++) {
            newLections[i] = lections[i];
        }

        newLections[newLections.length - 1] = new Lection(nameOfLection);
        return newLections;
    }

    public Lection[] deleteLectionByNumber(Lection[] lections, String numberOfLection) {
        try {
            if (lections == null
                || numberOfLection == null
                || numberOfLection.isBlank()
                || Integer.parseInt(numberOfLection) < 0
                || Integer.parseInt(numberOfLection) >= lections.length) {
                throw new LessonNotFoundException("Lection Not Found");
            }
        } catch (NumberFormatException e) {
            throw new LessonNotFoundException("Lection Not Found");
        }

        Lection[] newLections = new Lection[lections.length - 1];

        for (int i = 0; i < Integer.parseInt(numberOfLection); i++) {
            newLections[i] = lections[i];
        }

        for (int i = Integer.parseInt(numberOfLection) + 1; i < lections.length; i++) {
            newLections[i - 1] = lections[i];
        }

        return newLections;
    }

    public void showLectionByNumber(Lection[] lections, String numberOfLection) {
        if (lections == null || numberOfLection == null) {
            return;
        }

        try {
            if (numberOfLection.isBlank()
                || Integer.parseInt(numberOfLection) < 0
                || Integer.parseInt(numberOfLection) >= lections.length) {
                throw new LessonNotFoundException("Lection Not Found");
            }
        } catch (NumberFormatException e) {
            throw new LessonNotFoundException("Lection Not Found");
        }

        System.out.println(lections[Integer.parseInt(numberOfLection)].getName());
    }

    public void exit() {
        scanner.close();
        System.exit(0);
    }
}
