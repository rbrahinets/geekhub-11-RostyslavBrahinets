package validators;

import exceptions.InvalidArgumentException;
import exceptions.NotFoundException;
import exceptions.ValidationException;
import models.Role;
import sources.PersonSource;

import java.util.List;

public class PersonValidator {
    public void validate(String firstName, String lastName, List<String> contacts,
                         String gitHubNickname, String role) {
        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name is invalid");
        } else if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name is invalid");
        } else if (contacts == null) {
            throw new ValidationException("Contacts of person are invalid");
        } else if (gitHubNickname == null || gitHubNickname.isBlank()) {
            throw new ValidationException("GitHub nickname is invalid");
        } else if (isInvalidRole(role)) {
            throw new InvalidArgumentException("Role of person is invalid");
        }
    }

    public void validate(int id) {
        PersonSource personSource = PersonSource.getInstance();
        if (id < 0 || id >= personSource.getPeople().size()) {
            throw new NotFoundException("Person not found");
        }
    }

    private boolean isInvalidRole(String role) {
        boolean invalidRole = true;

        for (Role roleOfPerson : Role.values()) {
            if (role.equalsIgnoreCase(String.valueOf(roleOfPerson))) {
                invalidRole = false;
                break;
            }
        }

        return invalidRole;
    }
}
