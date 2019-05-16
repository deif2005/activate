package com.order.machine.base;

/**
 * @Description
 * @Author cjy
 * @Date 2019/2/15
 **/
//PageInfo.list	结果集
//PageInfo.pageNum	当前页码
//PageInfo.pageSize	当前页面显示的数据条目
//PageInfo.pages	总页数
//PageInfo.total	数据的总条目数
//PageInfo.prePage	上一页
//PageInfo.nextPage	下一页
//PageInfo.isFirstPage	是否为第一页
//PageInfo.isLastPage	是否为最后一页
//PageInfo.hasPreviousPage	是否有上一页
//PageHelper.hasNextPage	是否有下一页
public class Page {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String sortFiled = "create_time";
    private String sortRule = "asc";

    public Integer getPageNo() {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortFiled() {
        return sortFiled;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public String getSortRule() {
        return sortRule;
    }

    public void setSortRule(String sortRule) {
        this.sortRule = sortRule;
    }
}
