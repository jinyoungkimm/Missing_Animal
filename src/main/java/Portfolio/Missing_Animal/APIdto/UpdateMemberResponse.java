package Portfolio.Missing_Animal.APIdto;

import lombok.Data;

@Data
public class UpdateMemberResponse {

    Long memberId;

    Boolean complete;

    public UpdateMemberResponse(Long memberId,Boolean complete){

        this.memberId = memberId;
        this.complete = complete;
    }

    public UpdateMemberResponse(){}

}
