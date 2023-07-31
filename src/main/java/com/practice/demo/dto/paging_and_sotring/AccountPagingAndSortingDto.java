package com.practice.demo.dto.paging_and_sotring;

public class AccountPagingAndSortingDto extends PagingAndSortingDto {


    @Override
    public void initializeDefaultValues() {

        DEFAULT_PAGE = 0;
        DEFAULT_SIZE = 5;
        DEFAULT_SORT_BY = "accountName";
        DEFAULT_ORDER = "ASC";
    }
}
