package Portfolio.Missing_Animal.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class Pagination {

    private int countCurrent; // 현재 page의 Element 개수
    private int pageNumberCurrent; // 현재 page 번호
    private long countTotal; // 총 Elements 개수
    private int pageTotal; // 총 page 수
    private int itemsCountPerPage; // 한 페이지의 size


    public Pagination(int countCurrent,int pageNumberCurrent,long countTotal,int pageTotal,int itemsCountPerPage){

        this.countCurrent = countCurrent;
        this.pageNumberCurrent = pageNumberCurrent;

        this.countTotal = countTotal;
        this.pageTotal = pageTotal;

        this.itemsCountPerPage = itemsCountPerPage;

    }

    public Pagination(){}

}
