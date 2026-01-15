package com.ecomerceproject.personalproject.Service;

import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Model.VerificationToken;
import com.ecomerceproject.personalproject.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void createVerificationToken(User user, String token, LocalDateTime expiryDate) {
        VerificationToken verificationToken = VerificationToken.builder().user(user).token(token).expiryDate(expiryDate).build();
        verificationTokenRepository.save(verificationToken);
    }

}