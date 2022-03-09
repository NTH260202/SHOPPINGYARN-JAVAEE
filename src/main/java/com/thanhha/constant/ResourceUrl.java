package com.thanhha.constant;

import java.io.Serializable;

public class ResourceUrl implements Serializable {
    public static class PathValue {
        public static final String SEARCH_PAGE = "search.html";
        public static final String SEARCH_PAGE_RESULT = "search.jsp";
        public static final String ERROR_PAGE = "error.jsp";
        public static final String VIEW_PRODUCT_CATALOG = "productCatalog.jsp";
        public static final String VIEW_ITEMS_CART = "itemsOfCart.jsp";
        public static final String INVALID_ACCOUNT_PAGE = "invalidAccount.jsp";
        public static final String REGISTER_ERROR_PAGE = "registerError.jsp";
        public static final String LOGIN_PAGE = "login.html";
    }


    public static class PathName {
        public static final String SEARCH_PAGE = "searchPage";
        public static final String INVALID_ACCOUNT_PAGE = "invalidAccountPage";
        public static final String ERROR_PAGE = "errorPage";
        public static final String PRODUCT_PAGE = "viewProduct";
        public static final String CART_PAGE = "cartPage";
    }
}
