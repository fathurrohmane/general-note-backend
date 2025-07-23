package com.elkusnandi.generalnote.controller;

import com.elkusnandi.generalnote.common.base.BaseResponse;
import com.elkusnandi.generalnote.response.DistrictResponse;
import com.elkusnandi.generalnote.response.ProvinceResponse;
import com.elkusnandi.generalnote.response.RegencyResponse;
import com.elkusnandi.generalnote.response.VillageResponse;
import com.elkusnandi.generalnote.service.IndonesiaAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Indonesia Area Controller", description = "Manage Indonesia area information taken from 3rd party api.")
@Controller
@RequestMapping("/area")
public class IndonesiaAreaController {

    private final IndonesiaAreaService indonesiaAreaService;

    public IndonesiaAreaController(IndonesiaAreaService indonesiaAreaService) {
        this.indonesiaAreaService = indonesiaAreaService;
    }

    @Operation(
            summary = "Get list of province in Indonesia",
            description = "Return list of provinces in Indonesia taken from external api"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful fetch province list",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BaseResponse.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                           "data": [
                                             {
                                               "id": "11",
                                               "name": "ACEH"
                                             },
                                             {
                                               "id": "12",
                                               "name": "SUMATERA UTARA"
                                             }
                                           ],
                                           "success": true,
                                           "message": ""
                                         }
                                    """
                    )
            )
    )
    @GetMapping("/province")
    public BaseResponse<List<ProvinceResponse>> getProvince(@RequestParam(name = "q", required = false) String query) {
        return new BaseResponse<>(indonesiaAreaService.getProvinces(query), HttpStatus.OK, true, "");
    }

    @Operation(
            summary = "Get list of regency in Indonesia given province id",
            description = "Return list of regency in Indonesia given province id taken from external api"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful fetch regency list",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BaseResponse.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                            "data": [
                                              {
                                                "id": "3172",
                                                "shortId": "72",
                                                "name": "KOTA JAKARTA TIMUR"
                                              },
                                              {
                                                "id": "3173",
                                                "shortId": "73",
                                                "name": "KOTA JAKARTA PUSAT"
                                              }
                                            ],
                                            "success": true,
                                            "message": ""
                                          }
                                    """
                    )
            )
    )
    @GetMapping("/province/{provinceId}/regency")
    public BaseResponse<List<RegencyResponse>> getRegencies(
            @PathVariable(name = "provinceId") String provinceId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(indonesiaAreaService.getRegencies(provinceId, query), HttpStatus.OK, true, "");
    }

    @Operation(
            summary = "Get list of district in Indonesia given province id and regency id",
            description = "Return list of district in Indonesia given province id and regency id taken from external api"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful fetch district list",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BaseResponse.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                             "data": [
                                               {
                                                 "id": "3173010",
                                                 "shortId": "010",
                                                 "name": "TANAH ABANG"
                                               },
                                               {
                                                 "id": "3173020",
                                                 "shortId": "020",
                                                 "name": "MENTENG"
                                               }
                                             ],
                                             "success": true,
                                             "message": ""
                                           }
                                    """
                    )
            )
    )
    @GetMapping("/province/{provinceId}/regency/{regencyId}/district")
    public BaseResponse<List<DistrictResponse>> getDistricts(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(
                indonesiaAreaService.getDistricts(provinceId, regencyId, query),
                HttpStatus.OK,
                true,
                ""
        );
    }

    @Operation(
            summary = "Get list of village in Indonesia given province id, regency id and district id",
            description = "Return list of village in Indonesia given province id, regency id and district id taken from external api"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Successful fetch village list",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BaseResponse.class),
                    examples = @ExampleObject(
                            value = """
                                        {
                                              "data": [
                                                {
                                                  "id": "3173010001",
                                                  "shortId": "001",
                                                  "name": "GELORA"
                                                },
                                               {
                                                  "id": "3173010006",
                                                  "shortId": "006",
                                                  "name": "KEBON KACANG"
                                                }
                                              ],
                                              "success": true,
                                              "message": ""
                                            }
                                    """
                    )
            )
    )
    @GetMapping("/province/{provinceId}/regency/{regencyId}/district/{districtId}/village")
    public BaseResponse<List<VillageResponse>> getVillages(
            @PathVariable(name = "provinceId") String provinceId,
            @PathVariable(name = "regencyId") String regencyId,
            @PathVariable(name = "districtId") String districtId,
            @RequestParam(name = "q", required = false) String query
    ) {
        return new BaseResponse<>(
                indonesiaAreaService.getVillages(provinceId, regencyId, districtId, query),
                HttpStatus.OK,
                true,
                ""
        );
    }
}
