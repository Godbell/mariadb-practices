package bookmall.vo;

public class CategoryVo {
    private int no;
    private String name;

    public CategoryVo(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public CategoryVo(String name) {
        this.no = -1;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }
}
