package flab.project.common;

import lombok.Getter;

@Getter
public enum Constraints {
    NUMBER_LIMIT_OF_SOCIAL_ACCOUNTS(3);

    private int value;

    Constraints(int value) {
        this.value = value;
    }
}
