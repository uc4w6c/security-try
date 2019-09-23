package com.example.demo.service;

import com.example.demo.domain.Account;
import com.example.demo.domain.PasswordReissueInfo;
import com.example.demo.exception.NotFoundException;
import com.example.demo.form.UserCreateForm;
import com.example.demo.form.UserPasswordChangeForm;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PasswordReissueInfoRepository;
import org.seasar.doma.jdbc.Result;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordReissueInfoRepository passwordReissueInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordReissueInfoRepository passwordReissueInfoRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordReissueInfoRepository = passwordReissueInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * アカウント仮登録メソッド
     * TODO: formに依存しちゃってるから改善したい
     * TODO: ユーザーが存在する場合のエラーチェックを作成すること
     * TODO: トークンの有効期限を設定すべし
     * @param form
     * @return
     */
    @Transactional // 一旦ここにトランザクション追加
    public Account create(UserCreateForm form) {
        String passwordDigest = passwordEncoder.encode(form.getPassword());
        String activationDigest = UUID.randomUUID().toString();
        Account account = new Account(form.getEmail(), passwordDigest, form.getBirthday(), activationDigest, false);

        Result<Account> result = accountRepository.save(account);

        return result.getEntity();
    }

    /**
     * アカウント有効化tokenからユーザーを取得
     * @param activationDigest
     * @return
     */
    public Account accountEnable(String activationDigest) {
        Account disableAccount = accountRepository.findByActivationDigest(activationDigest)
                                                    .orElseThrow(() -> new NotFoundException());

        accountRepository.enableAccount(disableAccount.getEmail());
        return disableAccount;
    }


    public PasswordReissueInfo reissueCreate(String email, Date birthday) {
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

    /**
     * パスワード変更メソッド
     * @param email
     * @param plainPassword
     * @return void
     */
    public void passwordChange(String email, String plainPassword) {
        String passwordDigest = passwordEncoder.encode(plainPassword);
        accountRepository.passwordChange(email, passwordDigest);
    }

    public Account find(String email) {
        return accountRepository.findByEmail(email);
    }
}
