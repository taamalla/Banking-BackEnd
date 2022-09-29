package tn.esprit.banking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationByStatus {
    private int id;
    private int executed;
    private int to_be_executed;
    private int cancelled;
}
