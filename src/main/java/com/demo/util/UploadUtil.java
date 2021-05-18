package com.demo.util;

import com.demo.constant.CommonConstant;
import com.demo.entity.Result;
import com.demo.entity.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UploadUtil {

    /**
     * @throws
     * @Title: uploadPic
     * @Description: 多文件上传
     * @param: @param multipartFiles
     * @param: @param secFile
     * @param: @return
     * @return: String
     * @date: 2020年5月19日 上午10:25:53
     */
    public String uploadPic(MultipartFile[] multipartFiles, String secFile) {
        String path = "";
        String realpath = CommonConstant.FILE_ADDRESS_PREFIX + secFile;
        CreatFileDir(realpath);
        String filename = null;
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                if (multipartFile.isEmpty()) {
                    break;
                }
                filename = UUID.randomUUID().toString().replaceAll("-", "");
                multipartFile.transferTo(new File(realpath, filename));
                path = path + CommonConstant.FILE_URL_PREFIX + secFile + filename + ",";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (StringUtil.isNotEmpty(path)) {
            path = path.substring(0, path.length() - 1);
        }
        if (path == "") {
            return null;
        }
        return path;
    }


    /**
     * @throws
     * @Title: uploadPic
     * @Description: 单文件上传
     * @param: @param multipartFile
     * @param: @param secFile
     * @param: @return
     * @return: String
     * @date: 2020年5月19日 上午10:26:04
     */
    public String uploadContentPic(MultipartFile multipartFile, String secFile) {
        String path = "";
        Date date = new Date();
        SimpleDateFormat mmFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        String tail = mmFormat.format(date) + "/";
        secFile += tail;
        String realpath = CommonConstant.FILE_ADDRESS_PREFIX + secFile;
        CreatFileDir(realpath);
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        String filename = format.format(date);
        try {
            if (multipartFile.isEmpty()) {
                return null;
            }
            filename = filename + StringUtil.getRandomChar(6);
            multipartFile.transferTo(new File(realpath, filename));
            path = path + CommonConstant.FILE_URL_PREFIX + secFile + filename + ",";
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(path)) {
            path = path.substring(0, path.length() - 1);
        }
        if (path == "") {
            path = null;
        }
        /**AjaxResult ajax = AjaxResult.success();
         ajax.put("fileName", filename);
         ajax.put("url", path);*/
        return Result.fileResult(StatusCode.OK, "操作成功", filename, path);
    }

    public String uploadPic(MultipartFile multipartFile, String secFile) {
        String path = "";
        Date date = new Date();
        SimpleDateFormat mmFormat = new SimpleDateFormat("yyyyMM", Locale.getDefault());
        String tail = mmFormat.format(date) + "/";
        secFile += tail;
        String realpath = CommonConstant.FILE_ADDRESS_PREFIX + secFile;
        CreatFileDir(realpath);
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        String filename = format.format(date);
        try {
            if (multipartFile.isEmpty()) {
                return null;
            }
            filename = filename + StringUtil.getRandomChar(6);
            multipartFile.transferTo(new File(realpath, filename));
            path = path + CommonConstant.FILE_URL_PREFIX + secFile + filename + ",";
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(path)) {
            path = path.substring(0, path.length() - 1);
        }
        if (path == "") {
            path = null;
        }
        /**AjaxResult ajax = AjaxResult.success();
         ajax.put("fileName", filename);
         ajax.put("url", path);*/
        return path;
    }

    protected void CreatFileDir(String filepath) {
        File dir = new File(filepath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

}
