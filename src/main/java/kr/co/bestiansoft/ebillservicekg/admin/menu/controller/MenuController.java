package kr.co.bestiansoft.ebillservicekg.admin.menu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.bestiansoft.ebillservicekg.admin.menu.service.MenuService;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.MenuVo;
import kr.co.bestiansoft.ebillservicekg.admin.menu.vo.QuickMenuVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = "Menu management API")
public class MenuController {
    private final MenuService menuService;

    @ApiOperation(value = "menu List check", notes = "menu List Inquiry.")
    @GetMapping("admin/menu")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuList(param)), HttpStatus.OK);
    }
    
    // addition(20250210 Jinho Cho)
    @ApiOperation(value = "Department menu List check", notes = "Department menu List Inquiry.")
    @GetMapping("admin/deptmenu")
    public ResponseEntity<CommonResponse> getDeptMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getDeptMenuList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Menu details check", notes = "menu Details Inquiry.")
    @GetMapping("admin/menu/{menuId}")
    public ResponseEntity<CommonResponse> getMenuDetail(@PathVariable Long menuId, @RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuDetail(menuId, param)), HttpStatus.OK);
    }

    @ApiOperation(value = "menu generation", notes = "Menu Create.")
    @PostMapping("admin/menu")
    public ResponseEntity<CommonResponse> createMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code created successfully.", menuService.createMenu(menuVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "menu correction", notes = "Menu Modify.")
    @PutMapping("admin/menu")
    public ResponseEntity<CommonResponse> updateMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code updated successfully", menuService.updateMenu(menuVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "menu delete", notes = "menu Delete.")
    @DeleteMapping("admin/menu")
    public ResponseEntity<CommonResponse> deleteMenu(@RequestBody List<Long> menuIds) {
        menuService.deleteMenu(menuIds);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Menu code deleted successfully."), HttpStatus.OK);
    }

    //--------quick menu--------
    @ApiOperation(value = "Quick menu List check", notes = "Quick menu List Inquiry.")
    @GetMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> getQuickMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getQuickMenuList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "Quick menu generation", notes = "Quick menu List Create.")
    @PostMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> createQuickMenu(@RequestBody QuickMenuVo quickMenuVo) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.createQuickMenu(quickMenuVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "Quick menu delete", notes = "Quick menu List Delete.")
    @DeleteMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> deleteQuickMenu(@RequestBody QuickMenuVo quickMenuVo) {
        menuService.deleteQuickMenu(quickMenuVo);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Deleted quick menu successfully."), HttpStatus.OK);
    }

}
