package Portfolio.Missing_Animal.propertiesWithJava.QueryrestApi.querycontroller;

import Portfolio.Missing_Animal.propertiesWithJava.QueryrestApi.queryrepository.ReportQueryRepository;
import Portfolio.Missing_Animal.domainEntity.Report;
import Portfolio.Missing_Animal.dto.ReportDto;
import Portfolio.Missing_Animal.dto.ReportDtoWithPagination;
import Portfolio.Missing_Animal.service.serviceinterface.StorageServiceForReport;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportQueryController {

    private final ReportQueryRepository reportQueryRepository;


    private final StorageServiceForReport storageService;



    //@GetMapping("")
    public ReportDtoWithPagination getAllReport(@RequestParam(value = "offset",defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "2") int limit){

        int pageNumber = (offset / limit) ; // limit != 0 이라는 보장이 있어야 한다. JPA는 PAGE가 0번부터 시작!
        int size = limit;

        ReportDtoWithPagination result = reportQueryRepository.findAllWithPaging2(pageNumber, size);


        return result;

    }

    @GetMapping("")
    public ReportDtoWithPagination getAllReportV2(@PageableDefault(page = 0,size = 2,sort = "id", direction = Sort.Direction.ASC) Pageable pageable){


        ReportDtoWithPagination result = reportQueryRepository.findAllWithPaging3(pageable);


        return result;

    }

    @GetMapping("/{reportId}")
    public ReportDto getReportWithId(@PathVariable("reportId") Long reportId){

        try {
            ReportDto reportDto = reportQueryRepository.findById(reportId);

            return reportDto;
        }
        catch (NonUniqueResultException e){

            throw new IllegalStateException("해당 id의 Report가 2개 이상 조회됨");

        }
        catch (NoResultException e){

            throw new IllegalStateException("해당 id의 Report가 조회되지 않음");

        }
    }


    @GetMapping("/{reportId}/image")
    @ResponseBody
    @Transactional
    public ResponseEntity<Resource> serveFile(@PathVariable("reportId") Long reportId) throws UnsupportedEncodingException {

      try {

          Report report = reportQueryRepository.findByIdV2(reportId);

          String filename = report.getFileName();

          Resource file = storageService.loadAsResource(filename);


          return ResponseEntity.status(HttpStatus.OK)
                  .contentType(MediaType.valueOf("image/png"))
                  .body(file);

      }
      catch (NonUniqueResultException e){
          throw new IllegalStateException("결과가 2개 이상 조회됨");
      }
      catch (NoResultException e){

          throw new IllegalStateException("해당 id로 조회되는 것이 없음");
      }



    }


}
