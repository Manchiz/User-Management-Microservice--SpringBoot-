package com.Qouro.HRMManagementUser.UIControllers;


import com.Qouro.HRMManagementUser.UIModel.CreateUserRequesModel;
import com.Qouro.HRMManagementUser.UIModel.CreateUserResponceModel;
import com.Qouro.HRMManagementUser.UIModel.UserResponseModel;
import com.Qouro.HRMManagementUser.UserService.UserService;
import com.Qouro.HRMManagementUser.usersShared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserControllers {

    @Autowired
    private Environment env;

    @Autowired
    UserService userService;

    @GetMapping("/status/check")
//    @GetMapping
    public String status(){

        return "Working on port" + env.getProperty("local.server.port") + ", with token =" + env.getProperty("token.secret");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponceModel> createUser(@Valid @RequestBody CreateUserRequesModel UserDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = modelMapper.map(UserDetails, UserDto.class);
        UserDto createUser = userService.createUser(userDto);

        CreateUserResponceModel returnValue = modelMapper.map(createUser, CreateUserResponceModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){

        UserDto userDto = userService.getUserDetailsById(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
