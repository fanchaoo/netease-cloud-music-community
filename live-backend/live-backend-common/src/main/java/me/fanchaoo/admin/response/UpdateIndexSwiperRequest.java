package me.fanchaoo.admin.response;

import lombok.Data;
import me.fanchaoo.web.dto.IndexSwiperDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateIndexSwiperRequest {

    @NotNull
    @Size(min = 0, max = 8)
    private List<IndexSwiperDTO> list;
}
