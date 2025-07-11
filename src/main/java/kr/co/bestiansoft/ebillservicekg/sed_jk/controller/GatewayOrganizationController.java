package kr.co.bestiansoft.ebillservicekg.sed_jk.controller;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewayAllOrganizationsResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewayOrganizationResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.OrganizationEndpoint;
import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.dto.OrganizationAddDto;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping(value = Paths.ORGANIZATION)
@RequestMapping(value = "/organization")
public class GatewayOrganizationController {
    private final OrganizationEndpoint organizationEndpoint;

    @GetMapping("/edm-systems")
    public List<GatewayAllOrganizationsResponseDto.SedSystemsResponseDto.SedSystemResponseDto> listAll() {
        return organizationEndpoint.listAll();
    }

    @GetMapping("/other-list")
    public List<GatewayOrganizationResponseDto> otherList() {
        return organizationEndpoint.otherList();
    }

    @GetMapping("/my-list")
    public List<GatewayOrganizationResponseDto> myList(
            @RequestParam(required = false, defaultValue = "0") String page,
            @RequestParam(required = false, defaultValue = "50") String size
            ) {
        return organizationEndpoint.myList(page, size);
    }

    @PostMapping("/enable/{inn}")
    public OrganizationAddDto enableOrganization(@PathVariable String inn) {
        return organizationEndpoint.enable(inn);
    }

    @PostMapping("/disable/{inn}")
    public OrganizationAddDto disableOrganization(@PathVariable String inn) {
        return organizationEndpoint.disable(inn);
    }

    @PostMapping("/edit/{inn}")
    public OrganizationAddDto editOrganization(@PathVariable String inn, @RequestBody OrganizationAddDto organizationAddDto) {
        return organizationEndpoint.edit(inn, organizationAddDto);
    }

    @GetMapping("/search")
    public List<GatewayOrganizationResponseDto> search(
            @RequestParam(required = true) String name
    ) {
        return organizationEndpoint.search(name);
    }
}
