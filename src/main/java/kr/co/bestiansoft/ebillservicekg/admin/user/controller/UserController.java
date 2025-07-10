package kr.co.bestiansoft.ebillservicekg.admin.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import kr.co.bestiansoft.ebillservicekg.admin.user.service.UserService;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.controller.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Staff management API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @Operation(summary = "employee List check", description = "employee List Inquiry.")
    @GetMapping("admin/user")
    public ResponseEntity<CommonResponse> getUserList(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserList(param)), HttpStatus.OK);
    }

    @Operation(summary = "Staff check", description = "employee Details Inquiry.")
    @GetMapping("admin/user/{seq}")
    public ResponseEntity<CommonResponse> getUserDetail(@PathVariable Long seq) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserDetail(seq)), HttpStatus.OK);
    }

    @Operation(summary = "employee generation", description = "Employees Create.")
    @PostMapping("admin/user")
    public ResponseEntity<CommonResponse> createUser(@RequestBody UserVo userVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "User created successfully.", userService.createUser(userVo)), HttpStatus.OK);
    }

    @Operation(summary = "employee correction", description = "Employees Modify.")
    @PutMapping("admin/user")
    public ResponseEntity<CommonResponse> updateUser(@RequestBody UserVo userVo) {
        return new ResponseEntity<>(new CommonResponse(HttpStatus.CREATED.value(), "User updated successfully", userService.updateUser(userVo)), HttpStatus.OK);
    }

    @Operation(summary = "employee delete", description = "Employees Delete.")
    @DeleteMapping("admin/user")
    public ResponseEntity<CommonResponse> deleteUser(@RequestBody List<String> ids) {
        userService.deleteUser(ids);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "User code deleted successfully."), HttpStatus.OK);
    }

    //Department user check(concurrent position, committee, political party include) -- Departmental management, User management by authority
    @Operation(summary = "Department user check", description = "Users by department Inquiry.")
    @GetMapping("user/dept")
    public ResponseEntity<CommonResponse> getUserByDept(@RequestParam HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.getUserByDept(param)), HttpStatus.OK);
    }


    @Operation(summary = "user password reset", description = "user password reset")
    @PutMapping("user/reset/pswd")
    public ResponseEntity<CommonResponse> resetPswd(@RequestBody HashMap<String, Object> param) {
        return new ResponseEntity<>(new CommonResponse(200, "ok", userService.resetPswd(param)), HttpStatus.OK);
    }

    @Operation(summary = "user password correction", description = "user Password Modify.")
    @PutMapping("login/update")
    public ResponseEntity<CommonResponse> updatePswd(@RequestBody HashMap<String, Object> param) {
        userService.updatePswd(param);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "updated successfully"), HttpStatus.OK);
    }



    @Operation(summary = "Position correction", description = "Position Modify.")
    @PutMapping("admin/user/job")
    public ResponseEntity<CommonResponse> updateJob(@RequestBody HashMap<String, Object> param) {
        userService.updateJob(param);
        return new ResponseEntity<>(new CommonResponse(200, "ok", "updated successfully"), HttpStatus.OK);
    }


}
