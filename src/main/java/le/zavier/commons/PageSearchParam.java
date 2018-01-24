package le.zavier.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询参数实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
