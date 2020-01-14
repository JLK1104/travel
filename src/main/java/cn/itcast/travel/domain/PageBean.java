package cn.itcast.travel.domain;

public class PageBean {
    private int currentPage;
    private int lineSize;
    private String keyword;
    private String column;
    private int allRecorders;
    private int pageSize;
    public PageBean() {
    }

    public PageBean(int currentPage, int lineSize, String keyword, String column, int allRecorders, int pageSize) {
        this.currentPage = currentPage;
        this.lineSize = lineSize;
        this.keyword = keyword;
        this.column = column;
        this.allRecorders = allRecorders;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLineSize() {
        return lineSize;
    }

    public void setLineSize(int lineSize) {
        this.lineSize = lineSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getAllRecorders() {
        return allRecorders;
    }

    public void setAllRecorders(int allRecorders) {
        this.allRecorders = allRecorders;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "currentPage=" + currentPage +
                ", lineSize=" + lineSize +
                ", keyword='" + keyword + '\'' +
                ", column='" + column + '\'' +
                ", allRecorders=" + allRecorders +
                ", pageSize=" + pageSize +
                '}';
    }
}
