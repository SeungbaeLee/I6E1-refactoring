package main_project_025.I6E1.domain.trade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main_project_025.I6E1.domain.trade.entity.Status;


@Getter
@Setter
@NoArgsConstructor
public class TradePatchDto {

    @NotNull(message = "요청을 확인해주세요")
    private Status status;

    @JsonProperty("status")
    public void setStatus(String status) {
        if (status != null) {
            this.status = Status.from(status);
        }
    }
}