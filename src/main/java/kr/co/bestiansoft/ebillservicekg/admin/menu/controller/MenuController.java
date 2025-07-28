package kr.co.bestiansoft.ebillservicekg.admin.menu.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Menu management API")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "menu List check", description = "menu List Inquiry.")
    @GetMapping("admin/menu")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuList(param)), HttpStatus.OK);
    }

    // addition(20250210 Jinho Cho)
    @Operation(summary = "Department menu List check", description = "Department menu List Inquiry.")
    @GetMapping("admin/deptmenu")
    public ResponseEntity<CommonResponse> getDeptMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getDeptMenuList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Menu details check", description = "menu Details Inquiry.")
    @GetMapping("admin/menu/{menuId}")
    public ResponseEntity<CommonResponse> getMenuDetail(@PathVariable Long menuId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuDetail(menuId, param)), HttpStatus.OK);
    }

    @Operation(summary = "menu generation", description = "Menu Create.")
    @PostMapping("admin/menu")
    public ResponseEntity<CommonResponse> createMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code created successfully.", menuService.createMenu(menuVo)), HttpStatus.OK);
    }

    @Operation(summary = "menu correction", description = "Menu Modify.")
    @PutMapping("admin/menu")
    public ResponseEntity<CommonResponse> updateMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code updated successfully", menuService.updateMenu(menuVo)), HttpStatus.OK);
    }

    @Operation(summary = "menu delete", description = "menu Delete.")
    @DeleteMapping("admin/menu")
    public ResponseEntity<CommonResponse> deleteMenu(@RequestBody List<Long> menuIds) {
        menuService.deleteMenu(menuIds);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Menu code deleted successfully."), HttpStatus.OK);
    }

    //--------quick menu--------
    @Operation(summary = "Quick menu List check", description = "Quick menu List Inquiry.")
    @GetMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> getQuickMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getQuickMenuList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Quick menu generation", description = "Quick menu List Create.")
    @PostMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> createQuickMenu(@RequestBody QuickMenuVo quickMenuVo) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.createQuickMenu(quickMenuVo)), HttpStatus.OK);
    }

    @Operation(summary = "Quick menu delete", description = "Quick menu List Delete.")
    @DeleteMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> deleteQuickMenu(@RequestBody QuickMenuVo quickMenuVo) {
        menuService.deleteQuickMenu(quickMenuVo);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Deleted quick menu successfully."), HttpStatus.OK);
    }

}
