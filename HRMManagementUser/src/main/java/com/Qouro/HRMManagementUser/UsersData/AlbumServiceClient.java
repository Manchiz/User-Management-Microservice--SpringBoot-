package com.Qouro.HRMManagementUser.UsersData;

import com.Qouro.HRMManagementUser.UIModel.AlbumResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

//@FeignClient(name="albums-ws", fallback = AlbumsFallback.class)
@FeignClient(name="albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumServiceClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbum(@PathVariable String id);
}

//@Component
//class AlbumsFallback implements AlbumServiceClient{
//
//    @Override
//    public List<AlbumResponseModel> getAlbum(String id) {
//        return new ArrayList<>();
//    }

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient>{

    @Override
    public AlbumServiceClient create(Throwable throwable) {

        return new AlbumServiceClientfallback(throwable);
    }


    class AlbumServiceClientfallback implements AlbumServiceClient{

        Logger logger = LoggerFactory.getLogger(this.getClass());

        private final Throwable throwable;

        AlbumServiceClientfallback(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public List<AlbumResponseModel> getAlbum(String id) {

            if (throwable instanceof FeignException && ((FeignException) throwable).status() == 404){
                logger.error("404 error took place when getAlbum was called with userId:"
                + id + ".Error message:" + throwable.getLocalizedMessage());
            } else {
                logger.error("Other error took place:" +throwable.getLocalizedMessage());
            }

            return new ArrayList<>();
        }
    }
}