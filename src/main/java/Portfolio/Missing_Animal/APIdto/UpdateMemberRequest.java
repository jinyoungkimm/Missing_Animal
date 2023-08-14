package Portfolio.Missing_Animal.APIdto;


import Portfolio.Missing_Animal.AddressForm;
import Portfolio.Missing_Animal.BirthDateForm;
import Portfolio.Missing_Animal.EmailForm;
import jakarta.persistence.Embedded;

import lombok.Data;

@Data
public class UpdateMemberRequest {


    private String username;


    private EmailForm email;


    private BirthDateForm birthDate;


    private String phoneNumber;

    public UpdateMemberRequest(){}


}
