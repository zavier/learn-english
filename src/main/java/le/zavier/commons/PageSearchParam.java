package le.zavier.commons;

/**
 * 分页查询参数实体类
 */
public class PageSearchParam {

    /**
     * 页码，页数从 1 开始计数
     */
    private Integer pageNum;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 查询内容
     */
    private String searchText;

    public PageSearchParam(Integer pageNum, Integer pageSize, String searchText) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.searchText = searchText;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
