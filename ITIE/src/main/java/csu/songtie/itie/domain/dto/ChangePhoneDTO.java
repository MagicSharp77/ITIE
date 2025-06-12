package csu.songtie.itie.domain.dto;

import lombok.Data;

@Data
public class ChangePhoneDTO {
    private String phone;
    private String newPhone;
    private String captcha;
}
