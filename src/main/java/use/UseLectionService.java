package use;

import logger.Logger;
import models.Lection;
import models.Person;
import services.HomeWorkService;
import services.LectionService;
import services.PersonService;
import services.ResourceService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UseLectionService {
    private final Scanner scanner = new Scanner(System.in);
    private final LectionService lectionService = new LectionService();
    private final UseResourceService useResourceService = new UseResourceService();
    private final UsePersonService usePersonService = new UsePersonService();
    private final UseHomeWorkService useHomeworkService = new UseHomeWorkService();

    public void showAllLections() {
        for (Lection lection : lectionService.getLections()) {
            Logger.info(getClass().getName(),
                String.format("%s: %s; %s; %s %s; %s%n",
                    lection.getName(),
                    lection.getDescribe(),
                    lection.getResources(),
                    lection.getLecturer().getFirstName(),
                    lection.getLecturer().getLastName(),
                    lection.getHomeWorks()
                ));
        }
    }

    public void addNewLection() {
        String name = getNameOfLection();
        String describe = getDescribeOfLection();
        useResourceService.addResources();
        Person lecturer = getLecturer();
        useHomeworkService.addHomeWorks();

        ResourceService resourceService = new ResourceService();
        HomeWorkService homeWorkService = new HomeWorkService();
        lectionService.addLection(name, describe, resourceService.getResources(),
            lecturer, homeWorkService.getHomeWorks());
    }

    public void deleteLectionById() {
        lectionService.deleteLection(getIdOfLection());
    }

    public void showLectionById() {
        Lection lection = lectionService.getLection(getIdOfLection());
        if (lection != null) {
            Logger.info(getClass().getName(),
                String.format("%s: %s; %s; %s %s; %s%n",
                    lection.getName(),
                    lection.getDescribe(),
                    lection.getResources(),
                    lection.getLecturer().getFirstName(),
                    lection.getLecturer().getLastName(),
                    lection.getHomeWorks()
                ));
        }
    }

    public void showResourcesGroupedByLecture() {
        for (Lection lection : lectionService.getLections()) {
            Logger.info(getClass().getName(), String.format(
                "%n%s: %s%n",
                lection.getName(),
                lectionService.getResourcesGroupedByLecture().get(lection)
            ));
        }
    }

    public void showHomeWorksGroupedByLecture() {
        for (Lection lection : lectionService.getLections()) {
            Logger.info(getClass().getName(), String.format(
                "%n%s: %s%n",
                lection.getName(),
                lectionService.getHomeWorksGroupedByLecture().get(lection)
            ));
        }
    }

    public void closeScanner() {
        useResourceService.closeScanner();
        useHomeworkService.closeScanner();
        scanner.close();
    }

    private String getNameOfLection() {
        System.out.print("\nInput name of lection: ");
        return scanner.nextLine();
    }

    private String getDescribeOfLection() {
        System.out.print("Input describe of lection: ");
        return scanner.nextLine();
    }

    private Person getLecturer() {
        usePersonService.addPeople();
        return new PersonService().getPerson(0);
    }

    private int getIdOfLection() {
        System.out.print("Input id of lection: ");
        int id;

        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            id = -1;
            scanner.nextLine();
        }

        return id;
    }
}
