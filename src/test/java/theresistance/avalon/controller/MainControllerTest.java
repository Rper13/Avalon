package theresistance.avalon.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import theresistance.avalon.model.User;
import theresistance.avalon.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private MainController mainController;

    @Test
    void testLoginSuccess() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("testPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .param("username", "testUser")
                        .param("password", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("home"));
    }

    @Test
    void testLoginFailure_InvalidPassword() throws Exception {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        mockUser.setPassword("correctPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .param("username", "testUser")
                        .param("password", "wrongPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(""));
    }

    @Test
    void testLoginFailure_UserNotFound() throws Exception {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/login")
                        .param("username", "nonexistentUser")
                        .param("password", "anyPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(""));
    }

    @Test
    void testGoHome() throws Exception {
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("profile"));
    }
}
