package com.order.machine.upload;

import com.order.machine.ImportInfoConfig;
import com.order.machine.dto.UploadFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author miou
 * @date 2018-08-14
 */
@Component
public class UploadFile {
    private static Logger logger = LoggerFactory.getLogger(UploadFile.class);

    @Autowired
    private ImportInfoConfig importInfoConfig;

    /**
     * 上传文件方法，支持多文件同时上传
     * @param request
     * @param updateDir 上传文件的相对路径，如（/paper/）
     * @return (返回代码说明：0=上传成功；1=没有文件上传；2=上传失败；3=上传异常；4=上传文件类型错误)
     */
    public UploadFileResponse uploadFile(HttpServletRequest request, String updateDir) throws IOException {
        UploadFileResponse resp = new UploadFileResponse();
        List<String> fileList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        try {
//            String basePath = LocalZkConfig.getJyPackageImportPath();
            String basePath = importInfoConfig.getResourceImportPath();
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            multipartResolver.setDefaultEncoding("utf-8");
            //判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                //转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                //取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                int count = 0;
                while (iter.hasNext()) {
                    count++;
                    //取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        //取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (myFileName.trim() != "") {
                            //获取文件后缀
                            String suffix = myFileName.substring(myFileName.lastIndexOf("."));
                            //上传文件名使用uuid
                            myFileName = file.getOriginalFilename();//+ "-" + file.getOriginalFilename() + DateUtil.getDateTime() + suffix
                            //获取完整服务器上传文件路径(上传目录根路径+相对路径+文件名称)
                            String uploadServerPath = basePath + updateDir +  myFileName;
                            File localFile = new File(uploadServerPath);
                            if (!localFile.exists()) {
                                localFile.mkdirs();
                            }
                            if (localFile.isFile()) {
                                localFile.delete();
                            }
                            try {
                                file.transferTo(localFile);
                            } catch (IOException e) {
                                throw e;
                            }
                            //增加至返回文件列表中
                            fileList.add(uploadServerPath);
                            //增加至返回文件名称列表中
                            fileNameList.add(file.getOriginalFilename());
                        }
                    }
                }
                //没有文件上传
                if (count == 0) {
                    throw new RuntimeException("没有文件上传");
                }
                logger.debug("上传文件成功---------------[" + fileList + "]");
            }
            //不存在文件流
            else {
                throw new RuntimeException("未上传文件");
            }
        } catch (Exception e) {
            throw e;
        }
        resp.setFileList(fileList);
        resp.setOriginalFileNameList(fileNameList);
        return resp;
    }

    public UploadFileResponse uploadFile2(List<MultipartFile> files ,String uploadDir) throws IOException {
        UploadFileResponse resp = new UploadFileResponse();
        List<String> uploadFileList = new ArrayList<>();
        List<String> originalNameList = new ArrayList<>();
        String basePath = importInfoConfig.getResourceImportPath();
        if (files.size() > 0) {
            for (MultipartFile file : files) {
                // 判断是否为空文件
                if (file.isEmpty())
                    continue;
//                String contentType = file.getContentType();
//                String fileName = file.getName();
//                Long fileSize = file.getSize();
                // 原文件名即上传的文件名
                String origFileName = file.getOriginalFilename();
                String uploadServerPath = basePath + uploadDir +  origFileName;
                try {
                    File localFile = new File(uploadServerPath);
                    if (!localFile.exists()) {
                        localFile.mkdirs();
                    }
                    if (localFile.isFile()) {
                        localFile.delete();
                    }
                    file.transferTo(localFile);
                    //增加至返回文件列表中
                    uploadFileList.add(uploadServerPath);
                    //增加至返回文件名称列表中
                    originalNameList.add(origFileName);
                }catch (IOException e){
                    throw e;
                }
                resp.setFileList(uploadFileList);
                resp.setOriginalFileNameList(originalNameList);
            }
        }
        return resp;
    }
}
