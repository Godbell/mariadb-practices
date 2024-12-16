package bookmall.vo;

public class OrderVo {
    private Long no;
    private String number;
    private String title;
    private int price;
    private Long userNo;
    private String address;
    private String state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPayment() {
        return price;
    }

    public void setPayment(int price) {
        this.price = price;
    }

    public Long getUserNo() {
        return userNo;
    }

    public void setUserNo(Long userNo) {
        this.userNo = userNo;
    }

    public String getShipping() {
        return address;
    }

    public void setShipping(String address) {
        this.address = address;
    }

    public String getStatus() {
        return state;
    }

    public void setStatus(String state) {
        this.state = state;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }
}
