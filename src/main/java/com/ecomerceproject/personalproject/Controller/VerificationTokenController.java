package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Model.VerificationToken;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import com.ecomerceproject.personalproject.Repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class VerificationTokenController {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public VerificationTokenController(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);

        if (optionalToken.isEmpty()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Token inválido.");
            return "verified";
        }

        VerificationToken verificationToken = optionalToken.get();

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Token expirado.");
            return "verified";
        }

        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        model.addAttribute("success", true);
        model.addAttribute("message", "¡Cuenta verificada exitosamente!");
        return "verified";
    }

}
