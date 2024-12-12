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
@Api(tags = "메뉴관리 API")
public class MenuController {
    private final MenuService menuService;

    @ApiOperation(value = "메뉴 리스트 조회", notes = "메뉴 리스트를 조회한다.")
    @GetMapping("admin/menu")
    public ResponseEntity<CommonResponse> getMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴상세 조회", notes = "메뉴 상세를 조회한다.")
    @GetMapping("admin/menu/{menuId}")
    public ResponseEntity<CommonResponse> getMenuDetail(@PathVariable Long menuId) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getMenuDetail(menuId)), HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 생성", notes = "메뉴를 생성한다.")
    @PostMapping("admin/menu")
    public ResponseEntity<CommonResponse> createMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code created successfully.", menuService.createMenu(menuVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 수정", notes = "메뉴를 수정한다.")
    @PutMapping("admin/menu")
    public ResponseEntity<CommonResponse> updateMenu(@RequestBody MenuVo menuVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "Menu code updated successfully", menuService.updateMenu(menuVo)), HttpStatus.OK);
    }

    @ApiOperation(value = "메뉴 삭제", notes = "메뉴 삭제한다.")
    @DeleteMapping("admin/menu")
    public ResponseEntity<CommonResponse> deleteMenu(@RequestBody Long menuId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "Menu code deleted successfully."), HttpStatus.OK);

    }

    //--------quick menu--------
    @ApiOperation(value = "퀵메뉴 리스트 조회", notes = "퀵메뉴 리스트를 조회한다.")
    @GetMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> getQuickMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.getQuickMenuList(param)), HttpStatus.OK);
    }

    @ApiOperation(value = "퀵메뉴 생성", notes = "퀵메뉴 리스트를 생성한다.")
    @PostMapping("admin/quickMenu")
    public ResponseEntity<CommonResponse> createQuickMenuList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", menuService.createQuickMenuList(param)), HttpStatus.OK);
    }

}
