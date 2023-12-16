package study.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import study.cafekiosk.spring.client.mail.MailSendClient;
import study.cafekiosk.spring.domain.history.mail.MailSendHistory;
import study.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

//    @Spy
    @Mock
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        // given
//        when(mailService.sendMail(anyString(),anyString(),anyString(),anyString()))
//                .thenReturn(true);

        given(mailService.sendMail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        // spy 사용할 때
        // sendMail만 stubbing 되고, 나머지는 실제 객체로 동작하게 한다.
//        doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(),anyString(),anyString(),anyString());

        // when
        Boolean result = mailService.sendMail("", "", "", "");


        // then
        assertThat(result).isTrue();
        verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}