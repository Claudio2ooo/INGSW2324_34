package it.unina.dietideals24.service.implementation;

import it.unina.dietideals24.model.DietiUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DietiUserServiceTest {

    DietiUserService dietiUserService = new DietiUserService();

    DietiUser toBeUpdated;
    DietiUser newDietiUser;

    @BeforeEach
    void setup() {
        toBeUpdated = new DietiUser("Mario", "Rossi", "example@mail.com", "foo", "Napoli", null);
        newDietiUser = new DietiUser("Mariano", "Rossi", "example@mail.com", "Extended biography", "Napoli", null);
    }

    @Test
    void updateDataBothNotNull() {
        dietiUserService.updateData(toBeUpdated, newDietiUser);
        assertEquals(new DietiUser("Mariano", "Rossi", "example@mail.com", "Extended biography", "Napoli" , null), toBeUpdated);
    }

    @Test
    void updateDataToBeUpdatedNullNewDietiUserNotNull() {
        assertThrows(NullPointerException.class, () -> dietiUserService.updateData(null, newDietiUser));
    }

    @Test
    void updateDataToBeUpdatedNotNullNewDietiUserNull() {
        assertThrows(NullPointerException.class, () -> dietiUserService.updateData(toBeUpdated, null));
    }

    @Test
    void updateDataBothNull() {
        assertThrows(NullPointerException.class, () -> dietiUserService.updateData(null, null));
    }
}