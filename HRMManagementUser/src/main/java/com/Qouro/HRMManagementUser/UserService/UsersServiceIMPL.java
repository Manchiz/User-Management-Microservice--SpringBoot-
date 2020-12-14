package com.Qouro.HRMManagementUser.UserService;


import com.Qouro.HRMManagementUser.UIModel.AlbumResponseModel;
import com.Qouro.HRMManagementUser.UserEntity.UserEntity;
import com.Qouro.HRMManagementUser.UserEntity.usersReposirories;
import com.Qouro.HRMManagementUser.UsersData.AlbumServiceClient;
import com.Qouro.HRMManagementUser.usersShared.UserDto;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
//@EnableAutoConfiguration
public class UsersServiceIMPL implements UserService {

    usersReposirories usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
//    RestTemplate restTemplate;
    Environment environment;
    AlbumServiceClient albumServiceClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UsersServiceIMPL(usersReposirories usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AlbumServiceClient albumServiceClient, Environment environment) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.restTemplate = restTemplate;
        this.albumServiceClient = albumServiceClient;
        this.environment = environment;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
//        userEntity.setEncryptedPassword("test");

        usersRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsById(String userId) {

        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);

//        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);
//
//        ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
//        });
//
//        List<AlbumResponseModel> albumList = albumListResponse.getBody();

        logger.info("Before calling albums Microservice");

        List<AlbumResponseModel> albumList = albumServiceClient.getAlbum(userId);

        logger.info("After calling albums Microservice");

//        Disable because of expection haddle class implemented

//        List<AlbumResponseModel> albumList = null;
//        try {
//            albumList = albumServiceClient.getAlbum(userId);
//        } catch (FeignException e) {
////            e.printStackTrace();
//            logger.error(e.getLocalizedMessage());
//        }

        userDto.setAlbum(albumList);

        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = usersRepository.findByEmail(username);

        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true,true,true,true,new ArrayList<>());
    }
}
