package com.lot.iotsite.service;

import org.springframework.stereotype.Service;

@Service
public interface PictureService {
    Boolean deletePictureByCheckId(Long checkId);
}
