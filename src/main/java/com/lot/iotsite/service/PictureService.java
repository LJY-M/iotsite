package com.lot.iotsite.service;

import com.lot.iotsite.domain.Picture;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {
    Boolean deletePictureByCheckId(Long checkId);

    int insertPicture(Picture picture);
}
