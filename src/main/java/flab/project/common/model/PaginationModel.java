package flab.project.common.model;

import lombok.Getter;

@Getter
public class PaginationModel<T> {
    T data;
    boolean hasNextPage;

    public PaginationModel(T data, boolean hasNext) {
        this.data = data;
        this.hasNextPage = hasNext;
    }
}
