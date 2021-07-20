package com.demo.config;

import com.demo.util.ObsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @date 2021/5/13
 * @desc 华为云对象存储Obs配置文件
 */
@Component
public class ObsService {

    private ObsUtil obsUtil;

    @Value("${obs.endPoint}")
    public  String endPoint;
    @Value("${obs.ak}")
    public  String ak ;
    @Value("${obs.sk}")
    public  String sk;
    @Value("${obs.bucketName}")
    public  String bucketName;

    /**
     * 获取连接
     */
    public ObsUtil getObsUtil() {
        if(obsUtil == null) {
            obsUtil = new ObsUtil(ak,sk,endPoint);
            // 如果设置过CDN的路径测设置为CDN路径，没有设置则为桶原生的访问路径
            //obsHandler.setUrlForCDN(Global.get("ATTACHMENT_FILE_URL"));
            // 在数据库中读取进行操作的桶的明恒
            obsUtil.setBucketName(bucketName);
            // 对桶名称进行当前类内缓存
            bucketName = obsUtil.getBucketName();
        }
        return obsUtil;
    }

}
