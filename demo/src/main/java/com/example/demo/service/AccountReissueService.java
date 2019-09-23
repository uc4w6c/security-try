package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.domain.PasswordReissueInfo;
import com.example.demo.exception.NotFoundException;
import com.example.demo.form.UserCreateForm;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PasswordReissueInfoRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountReissueService {
    private final AccountRepository accountRepository;
    private final PasswordReissueInfoRepository passwordReissueInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountReissueService(AccountRepository accountRepository, PasswordReissueInfoRepository passwordReissueInfoRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordReissueInfoRepository = passwordReissueInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * パスワード再発行申請処理
     * MEMO: 責務多すぎるような気もする。別サービスに移動する？
     * @param email
     * @param birthday
     * @return PasswordReissueInfo
     */
    public PasswordReissueInfo reissueCreate(String email, LocalDate birthday) {
        // TODO: NotFoundから別のものに変更すること
        Account account = accountRepository.findByEmailAndBirthday(email, birthday)
                                            .orElseThrow(() -> new NotFoundException());

        String token = UUID.randomUUID().toString();
        // MEMO: 一旦決め打ちで現在時刻から15分間有効
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(15);

        Optional<PasswordReissueInfo> passwordReissueInfo = passwordReissueInfoRepository.findByEmail(email);
        Result<PasswordReissueInfo> newPasswordReissueInfo = passwordReissueInfo
                                                        .map(info -> {
                                                            return passwordReissueInfoRepository.update(
                                                                                info.tokenUpdate(token, expiryDate));
                                                        })
                                                        .orElseGet(() -> {
                                                            return passwordReissueInfoRepository.insert(
                                                                    new PasswordReissueInfo(email, token, expiryDate));
                                                        });

        return newPasswordReissueInfo.getEntity();
    }

    public PasswordReissueInfo findReissueInfo(String token) {
        LocalDateTime nowTime = LocalDateTime.now();
        return passwordReissueInfoRepository.findByToken(token, nowTime)
                                                .orElseThrow(() -> new NotFoundException());

    }
}
