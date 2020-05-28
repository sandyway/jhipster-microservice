package online.kehan.connect.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassWithTotalCount<T> {
    private T data;
    private int totalCount;
}
