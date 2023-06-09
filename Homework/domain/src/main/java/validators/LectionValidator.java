package validators;

import exceptions.NotFoundException;
import exceptions.ValidationException;
import models.Lection;
import repository.LectionRepository;

public class LectionValidator {
    private final LectionRepository lectionRepository;

    public LectionValidator(LectionRepository lectionRepository) {
        this.lectionRepository = lectionRepository;
    }

    public void validate(Lection lection) {
        String name = lection.getName();
        String describe = lection.getDescribe();

        if (name == null || name.isBlank()) {
            throw new ValidationException("Name of lection is invalid");
        } else if (describe == null || describe.isBlank()) {
            throw new ValidationException("Describe of lection is invalid");
        }
    }

    public void validate(int id) {
        if (id < 1 || id > lectionRepository.getLections().size()) {
            throw new NotFoundException("Lection not found");
        }
    }
}
