package unithon.team5.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unithon.team5.login.dto.LoginRequest;
import unithon.team5.member.Member;
import unithon.team5.member.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Transactional
    public String createToken(final LoginRequest loginRequest) {
        final String memberIdentifier = jwtProvider.decodeSub(loginRequest.token(), loginRequest.email());
        final Member registeredMember = memberRepository.findByIdentifier(memberIdentifier)
                .orElseGet(() -> registerMember(memberIdentifier, loginRequest.email(), loginRequest.getName()));
        final UUID id = registeredMember.getId();
        return jwtProvider.createAccessTokenWith(id);
    }

    private Member registerMember(final String memberAppleIdentifier, final String email, final Optional<String> name) {
        final String nickname = parseEmail(email);
        final String validateNickname = generateValidateNickname(nickname);
        final String memberName = name.orElse(validateNickname);

        final Member member = Member.builder()
                .nickname(validateNickname)
                .identifier(memberAppleIdentifier)
                .name(memberName)
                .build();

        return memberRepository.save(member);
    }

    private String generateValidateNickname(final String nickname) {
        String validateNickname = nickname;
        int num = 1;
        while (memberRepository.existsByNickname(validateNickname)) {
            validateNickname = nickname + "-" + num++;
        }
        return validateNickname;
    }

    private String parseEmail(final String email) {
        int atIndex = email.indexOf('@');
        return email.substring(0, atIndex);
    }
}
