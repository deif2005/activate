package com.order.machine.dto;

import java.io.Serializable;
import java.util.List;

public class UploadFileResponse implements Serializable{

    private static final long serialVersionUID = -1968837645835754263L;

    //上传至服务器后的文件列表
    private List<String> fileList;

    //原文件名
    private List<String> fileOriginalNameList;

    /**
     * 获取文件列表中第一个文件路径
     * @return
     */
    public String getFirstFile(){
        if(fileList.size() > 0){
            return fileList.get(0);
        }else{
            return null;
        }
    }

    /**
     * 获取文件列表中第一个文件名称
     * @return
     */
    public String getOriginalFirstFileName(){
        if(fileOriginalNameList.size() > 0){
            return fileOriginalNameList.get(0);
        }else{
            return null;
        }
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public List<String> getOriginalFileNameList() {
        return fileOriginalNameList;
    }

    public void setOriginalFileNameList(List<String> fileNameList) {
        this.fileOriginalNameList = fileNameList;
    }
}
