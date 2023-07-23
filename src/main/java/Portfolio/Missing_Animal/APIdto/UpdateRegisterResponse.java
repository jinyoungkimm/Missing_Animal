package Portfolio.Missing_Animal.APIdto;

import lombok.Data;

@Data
public class UpdateRegisterResponse {

    Long regiserId;

    Boolean complete;

    public UpdateRegisterResponse(Long regiserId,Boolean complete){
        this.regiserId = regiserId;
        this.complete = complete;
    }

    public UpdateRegisterResponse(){}

}
