package echochamber.user.api.v1;

import echochamber.user.service.UserSearchService;
import echochamber.user.service.change.UserChangeService;
import echochamber.user.service.change.UserChanges;
import echochamber.user.service.create.UserCreateService;
import echochamber.user.service.create.CreateUserRequest;
import echochamber.user.service.delete.UserDeleteRestoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserSearchService userSearchService;
    private final UserCreateService userCreateService;
    private final UserChangeService userChangeService;
    private final UserDeleteRestoreService userDeleteRestoreService;

    public UserController(UserSearchService userSearchService, UserCreateService userCreateService,
                          UserChangeService userChangeService, UserDeleteRestoreService userDeleteRestoreService) {
        this.userSearchService = userSearchService;
        this.userCreateService = userCreateService;
        this.userChangeService = userChangeService;
        this.userDeleteRestoreService = userDeleteRestoreService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        var user = userSearchService.getUser(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userDto = UserDto.fromUser(user.get());
        return ResponseEntity.ok(userDto);
    }

    //FIXME remove whe authorization will be ready
    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            userCreateService.createUser(createUserRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
