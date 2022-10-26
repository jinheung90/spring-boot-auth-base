package com.higherx.homework.domain.user.service;

import com.higherx.homework.errorHandling.customRuntimeException.RuntimeExceptionWithCode;
import com.higherx.homework.errorHandling.errorEnums.GlobalErrorCode;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@Tag("integration")
@ActiveProfiles("test")
@TestMethodOrder(value = MethodOrderer.DisplayName.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void init() {
        // 한글 깨짐현상

    }

    @Test
    @DisplayName("1 save user")
    public void saveUser() {
        userService.signup(
                "abcd",
                123,
                23,
                123456,
                "nick1",
                "01031230851",
                "abcd"
        );
    }
    @Test
    @DisplayName("2 verify account")
    public void verifyAccount() {
        Exception exception = Assertions.assertThrows(RuntimeExceptionWithCode.class, () -> userService.verifyUserLoginId("abcd"));
        Assertions.assertDoesNotThrow(() -> userService.verifyUserNickname("abcd2"));
    }

    @Test
    @DisplayName("3 verify nickname")
    public void verifyNickname() {
        Exception exception = Assertions.assertThrows(RuntimeExceptionWithCode.class, () -> userService.verifyUserNickname("nick1"));
        Assertions.assertDoesNotThrow(() -> userService.verifyUserNickname("Nick1"));
    }

    @Test
    @DisplayName("4 verify crn")
    public void verifyCrn() {
        Exception exception = Assertions.assertThrows(RuntimeExceptionWithCode.class, () -> userService.verifyCrnNumber(123,23,123456));
        Assertions.assertDoesNotThrow(() -> userService.verifyCrnNumber(123,23,1234));
    }
}
