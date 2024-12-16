package bookmall.vo;

public class BookVo {
    private Long no;
    private String title;
    private int price;
    private int categoryNo;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public BookVo(String title, int price) {
        this.no = null;
        this.title = title;
        this.price = price;
    }

    public int getCategoryNo() {
        return categoryNo;
    }
}
