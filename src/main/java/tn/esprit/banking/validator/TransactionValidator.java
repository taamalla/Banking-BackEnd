package tn.esprit.banking.validator;

import tn.esprit.banking.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidator {

    public static List<String> validate(Transaction transaction) {
        List<String> errors = new ArrayList<>();
        if (transaction == null) {
            errors.add("Can you set The Transaction Infos");
            return errors;
        }
        return errors;
    }
}
