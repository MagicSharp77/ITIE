package csu.songtie.itie.domain.entity.order;

import csu.songtie.itie.domain.vo.OrderVO;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private OrderVO orderVO;
    private List<LineItem> lineItemList;
}
