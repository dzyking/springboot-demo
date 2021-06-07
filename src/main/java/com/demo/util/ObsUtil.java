package com.demo.util;

import com.obs.services.ObsClient;
import com.obs.services.model.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ClassName :ObsUtil
 * @Description :Obs相关操作
 * @date :2021/5/13 15:24
 */
public class ObsUtil {

    private final String ak;// 华为云的 Access Key Id
    private final String sk;// 华为云的 Access Key Secret
    private final String endPoint; // 华为云连接的地址节点
    private String bucketName; // 创建的桶的名称
    private String url; // 访问OBS文件的url
    private static ObsClient obsClient; // 进行操作的华为云的客户端组件

    /**
     * 创建华为云OBS的本地控制器
     * @param ak
     * @param sk
     * @param endPoint
     */
    public ObsUtil(String ak, String sk, String endPoint) {
        this.ak = ak;
        this.sk = sk;
        this.endPoint = endPoint;
    }

    public ObsUtil(String ak, String sk, String endPoint, String bucketName) {
        this.ak = ak;
        this.sk = sk;
        this.endPoint = endPoint;
        this.bucketName = bucketName;
    }

    public ObsClient getObsClient() {
        if(obsClient == null) {
            obsClient = new ObsClient(ak, sk, endPoint);
        }
        return obsClient;
    }

    /**
     * 设置OBS访问的CDN路径
     * @param url
     */
    public void setUrlForCDN(String url) {
        this.url = url;
    }

    /**
     * 设置OBS操作的同桶名称
     * @param bucketName
     */
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 关闭当前的使用的OBSClient
     */
    public void closeOBSClient() {
        if(getObsClient() != null){
            try {
                getObsClient().close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得原生OBSBucket的访问前缀
     * @return  桶原生的访问前缀，即不经过CDN加速的访问路径
     */
    public String getOriginalUrlForOBS() {
        return "//" + bucketName + "." + endPoint.substring(8) + "/";
    }

    /**
     * 返回当前的创建桶的名称
     * @return 如果有桶，那么返回桶的名称，如果没有，则返回 null
     */
    public String getBucketName() {
        return this.bucketName;
    }

    /**
     *  返回当前的桶的访问路径 例：“ http://cdn.leimingyun.com/”
     * @return 若已经手动设置CDN路径返回为CND路径，反之则为OBS原始的访问路径
     */
    public String getUrl() {
        // 用户没有配置CDN，获的桶的原生访问路径
        if(url == null) {
            url = getOriginalUrlForOBS();
        }
        return url;
    }

    /**
     *  上传公共读文件
     */
    public PutObjectResult uploadPublicFile(File file, String name){
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(name);
        request.setFile(file);
        // 设置对象访问权限为公共读
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ);
        return getObsClient().putObject(request);
    }

    /**
     *  上传私有读文件
     */
    public PutObjectResult uploadPrivateFile(File file, String name){
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(name);
        request.setFile(file);
        // 设置对象访问权限为私有读
        request.setAcl(AccessControlList.REST_CANNED_PRIVATE);
        return getObsClient().putObject(request);
    }

    /**
     * 上传文件本地文件
     * @param bucketName 操作的桶的名称
     * @param filePath  上传的路径和文件名
     * @param localFile  需要上传的文件
     * @return
     */
    public PutObjectResult putLocalFile(String bucketName, String filePath, File localFile) {
        return getObsClient().putObject(bucketName, filePath, localFile);
    }

    /**
     * 上传文件流
     * @param bucketName 操作的桶的名称
     * @param filePath 上传的路径和文件名
     * @param inputStream  上传文件的输入流
     * @return
     */
    public PutObjectResult putFileByStream(String bucketName, String filePath, InputStream inputStream) {
        return getObsClient().putObject(bucketName, filePath, inputStream);
    }

    /**
     * 通过流上传文件并设置指定文件属性
     * @param bucketName  操作的桶的名称
     * @param filePath  上传的路径和文件名
     * @param inputStream  上传文件的输入流
     * @param metaData  上传文件的属性
     * @return
     */
    public PutObjectResult putFilebyInstreamAndMeta(String bucketName, String filePath, InputStream inputStream, ObjectMetadata metaData) {
        return getObsClient().putObject(bucketName, filePath, inputStream, metaData);
    }

    /**
     *  获得临时访问路径
     */
    public String getSignatureUrl(String filePath){
        TemporarySignatureRequest request = new TemporarySignatureRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(filePath);
        request.setRequestDate(new Date());
        // 10分钟后链接失效
        request.setExpires(600);
        TemporarySignatureResponse response = getObsClient().createTemporarySignature(request);
        return response.getSignedUrl();
    }

    /**
     * 下载ObsObject
     * @param filePath   需要下载的文件路径。 例："site/a.txt"
     * @return  下载文件的字节数组
     */
    public byte[] getFileByteArray(String bucketName, String filePath) throws IOException {
        ObsObject obsObject = getObsClient().getObject(bucketName, filePath);
        InputStream input = obsObject.getObjectContent();
        byte[] b = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        while ((len = input.read(b)) != -1){
            bos.write(b, 0, len);
        }
        bos.close();
        input.close();
        return bos.toByteArray();
    }

    /**
     * 为对象设置公共读
     * @param objectKey
     */
    public HeaderResponse setObjectAclPubilcRead(String objectKey){
        return  obsClient.setObjectAcl(bucketName, objectKey, AccessControlList.REST_CANNED_PUBLIC_READ);
    }

    /**
     * OBS内对象复制
     * @param sourceBucketName   源文件的桶名称 例："wangmarket1232311"
     * @param sourcePath  源文件的路径和文件名 例："site/2010/example.txt"
     * @param destBucketName  目标文件的桶名称 例："swangmarket34578345"
     * @param destPath 目标文件的路径和文件名 例："site/2010/example_bak.txt"
     */
    public void copyObject(String sourceBucketName, String sourcePath, String destBucketName, String destPath) {
        getObsClient().copyObject(sourceBucketName, sourcePath, destBucketName, destPath);
    }

    /**
     * 删除对象
     * @param bucketName 操作的桶的名称
     * @param filePath  需要删除的对象路径
     */
    public DeleteObjectResult deleteObject(String bucketName, String filePath) {
        return getObsClient().deleteObject(bucketName, filePath);
    }

    /**
     * 批量删除对象
     * @param bucketName 操作的桶的名称
     * @param filePathList  需要删除的对象路径集合
     */
    public DeleteObjectsResult deleteObjects(String bucketName, List<String> filePathList) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest();
        deleteObjectsRequest.setBucketName(bucketName);
        for (String filePath : filePathList) {
            deleteObjectsRequest.addKeyAndVersion(filePath);
        }
        return getObsClient().deleteObjects(deleteObjectsRequest);
    }

}
