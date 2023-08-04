import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesherpa.beerdispenser.app.dtos.AdminDto;
import com.codesherpa.beerdispenser.app.dtos.request.CreateAdminDto;
import com.codesherpa.beerdispenser.app.exceptions.ServerException;
import com.codesherpa.beerdispenser.app.models.Admin;
import com.codesherpa.beerdispenser.app.services.AdminService;
import com.codesherpa.beerdispenser.app.utils.ApiHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        try {
            List<Admin> admins = adminService.getAllAdmins();
            return new ResponseEntity<>(admins.stream().map(ApiHelper::toAdminDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAdminById(@PathVariable Long id) {
        try {
            Admin admin = adminService.getAdmin(id);
            if (admin != null) {
                AdminDto adminDto = ApiHelper.toAdminDto(admin);
                return new ResponseEntity<>(adminDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error fetching admin: " + id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> addAdmin(@Valid @RequestBody CreateAdminDto adminDto) {
        Admin admin = new Admin();
        admin.setName(adminDto.getName());

        try {
            admin = adminService.createAdmin(admin);
            return new ResponseEntity<>(ApiHelper.toAdminDto(admin), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ServerException("Error creating admin"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
