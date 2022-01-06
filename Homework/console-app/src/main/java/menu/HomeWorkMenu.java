package menu;

import exceptions.NotFoundException;
import exceptions.ValidationException;
import logger.Logger;
import models.HomeWork;
import services.HomeWorkService;

import java.util.List;
import java.util.Optional;

public class HomeWorkMenu extends Menu {
    private final HomeWorkService homeWorkService = new HomeWorkService();

    @Override
    public void runMenu() {
        System.out.println(
            """

                ###################################

                Home Works Menu
                1 - Show Home Works
                2 - Add Home Work
                3 - Delete Home Work
                4 - Show Home Work"""
        );

        switch (getCommand()) {
            case "1" -> showHomeWorks();
            case "2" -> addHomeWork();
            case "3" -> deleteHomeWork();
            case "4" -> showHomeWork();
            default -> throw new NotFoundException("Command not found");
        }
    }

    private void showHomeWorks() {
        Optional<List<HomeWork>> homeWorks = homeWorkService.getHomeWorks();
        if (homeWorks.isPresent()) {
            for (HomeWork homeWork : homeWorks.get()) {
                System.out.printf(
                    "%s: %s%n",
                    homeWork.getTask(),
                    homeWork.getDeadLine()
                );
            }
        }
    }

    public void addHomeWork() {
        try {
            System.out.println("\nNew Home Works");
            int count = getCount();
            for (int i = 0; i < count; i++) {
                System.out.print("\nTask: ");
                String task = getFromScanner();
                homeWorkService.addHomeWork(task, getDeadLine());
            }
        } catch (ValidationException e) {
            Logger.error(getClass().getName(), e.getMessage(), e);
        }
    }

    private void deleteHomeWork() {
        try {
            homeWorkService.deleteHomeWork(getId());
        } catch (NotFoundException e) {
            Logger.error(getClass().getName(), e.getMessage(), e);
        }
    }

    private void showHomeWork() {
        try {
            Optional<HomeWork> homeWork = homeWorkService.getHomeWork(getId());
            if (homeWork.isPresent()) {
                System.out.printf(
                    "%s: %s%n",
                    homeWork.get().getTask(),
                    homeWork.get().getDeadLine()
                );
            }
        } catch (NotFoundException e) {
            Logger.error(getClass().getName(), e.getMessage(), e);
        }
    }
}