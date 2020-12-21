package com.lot.iotsite.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lot.iotsite.domain.Picture;
import com.lot.iotsite.mapper.PictureMapper;
import com.lot.iotsite.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.nio.file.Watchable;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public Boolean deletePictureByCheckId(Long checkId) {
        QueryWrapper<Picture> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq(Picture.CHECK_ID,checkId);
        Assert.isTrue(1<=pictureMapper.delete(queryWrapper),"删除检查图片失败！");
        return true;
    }

    @Override
    public int insertPicture(Picture picture) {
        QueryWrapper<Picture> pictureQueryWrapper = new QueryWrapper<>();
        pictureQueryWrapper.eq(Picture.URL, picture.getUrl());
        Picture picture1 = pictureMapper.selectOne(pictureQueryWrapper);
        Assert.isNull(picture1,"该图片已存在");
        int flag = pictureMapper.insert(picture);
        return flag;
    }
}
