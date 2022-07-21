package echochamber.user.api.v1;

import echochamber.user.repository.UserRepository;
import echochamber.user.service.change.UserChangeService;
import echochamber.user.service.change.UserChanges;
import echochamber.user.service.delete.UserDeleteRestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserRepository userDao;
    private final UserChangeService userChangeService;
    private final UserDeleteRestoreService userDeleteRestoreService;

    public UserController(UserRepository userDao, UserChangeService userChangeService, UserDeleteRestoreService userDeleteRestoreService) {
        this.userDao = userDao;
        this.userChangeService = userChangeService;
        this.userDeleteRestoreService = userDeleteRestoreService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        var user = userDao.findUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userDto = UserDto.fromUser(user.get());
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> patchUser(@PathVariable("id") long id, @RequestBody UserChanges changes) {
        try {
            var changedUser = userChangeService.changeUser(id, changes);
            return ResponseEntity.ok(UserDto.fromUser(changedUser));
        } catch (UserChangeService.NoSuchUserException e) {
            return ResponseEntity.notFound().build();
        } catch (UserChangeService.InvalidChangeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        try {
            userDeleteRestoreService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserDeleteRestoreService.NoSuchUserException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // FIXME Remove this API or make it dev-only
    @PostMapping("{id}/restore")
    public ResponseEntity<?> restoreUser(@PathVariable("id") long id) {
        try {
            userDeleteRestoreService.restoreUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserDeleteRestoreService.NoSuchUserException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
