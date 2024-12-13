package example;

public class DepartmentVO {
    private long id;
    private String name;

    public DepartmentVO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " [id=" + id + ", name=" + name + "]";
    }
}
