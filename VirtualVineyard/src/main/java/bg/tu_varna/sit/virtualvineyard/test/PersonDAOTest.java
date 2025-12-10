package bg.tu_varna.sit.virtualvineyard.test;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class PersonDAOTest {

    private MockedStatic<PersonDAO> mockedPersonDAO;

    @BeforeEach
    void setUp() {
        mockedPersonDAO = mockStatic(PersonDAO.class, CALLS_REAL_METHODS);
    }

    @AfterEach
    void tearDown() {
        mockedPersonDAO.close();
    }

    @Test
    @DisplayName("Successful Authentication")
    void authenticate_shouldReturnPerson_whenCredentialsAreCorrect() {
        String username = "testUser";
        String password = "correctPassword";

        Person mockPerson = mock(Person.class);
        when(mockPerson.passwordMatch(password)).thenReturn(true);

        // --- FIXED LINE BELOW ---
        mockedPersonDAO.when(() -> PersonDAO.findByUsername(username))
                .thenReturn(mockPerson);
        // ------------------------

        Person result = PersonDAO.authenticate(username, password);

        assertEquals(mockPerson, result);
    }

    @Test
    @DisplayName("Authentication Fails - Incorrect Password")
    void authenticate_shouldReturnNull_whenPasswordIsIncorrect() {
        String username = "testUser";
        String incorrectPassword = "wrongPassword";

        Person mockPerson = mock(Person.class);
        when(mockPerson.passwordMatch(incorrectPassword)).thenReturn(false);

        // --- FIXED LINE BELOW ---
        mockedPersonDAO.when(() -> PersonDAO.findByUsername(username))
                .thenReturn(mockPerson);
        // ------------------------

        Person result = PersonDAO.authenticate(username, incorrectPassword);

        assertNull(result);
    }

    @Test
    @DisplayName("Authentication Fails - User Not Found")
    void authenticate_shouldReturnNull_whenUserNotFound() {
        String username = "unknownUser";
        String password = "anyPassword";

        // --- FIXED LINE BELOW ---
        mockedPersonDAO.when(() -> PersonDAO.findByUsername(username))
                .thenReturn(null);
        // ------------------------

        Person result = PersonDAO.authenticate(username, password);

        assertNull(result);
    }
}